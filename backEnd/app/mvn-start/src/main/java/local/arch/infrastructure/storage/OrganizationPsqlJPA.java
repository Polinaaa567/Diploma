package local.arch.infrastructure.storage;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import local.arch.application.interfaces.page.organization.IStorageOrganization;
import local.arch.domain.entities.MonthData;
import local.arch.domain.entities.YearData;
import local.arch.domain.entities.page.Event;
import local.arch.domain.entities.page.FAQData;
import local.arch.domain.entities.page.InfoCenter;
import local.arch.infrastructure.storage.model.ECenter;
import local.arch.infrastructure.storage.model.EEvent;
import local.arch.infrastructure.storage.model.EUserEvent;
import local.arch.infrastructure.storage.model.Efaq;

public class OrganizationPsqlJPA implements IStorageOrganization {

    Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

    private static final String[] RU_MONTHS = {
            "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
    };

    @PersistenceContext(unitName = "Volunteering")
    private EntityManager entityManager;

    @Override
    public List<FAQData> receiveFAQ() {
        List<Efaq> results = entityManager.createQuery("select p from Efaq p", Efaq.class).getResultList();

        List<FAQData> faq = new ArrayList<>();
        for (Efaq res : results) {
            FAQData f = new FAQData();
            f.setFaqID(res.getFaqID());
            f.setAnswer(res.getAnswer());
            f.setQuestion(res.getQuestion());

            faq.add(f);
        }

        return faq;
    }

    @Override
    public InfoCenter receiveInfoAboutOrganization() {

        ECenter result = entityManager.createQuery("Select p from ECenter p", ECenter.class).getSingleResult();

        InfoCenter center = new InfoCenter();
        center.setNameCenter(result.getName());
        center.setDescription(result.getDescription());
        center.setAddress(result.getAddress());
        center.setContacts(result.getContacts());
        center.setImageData(result.getImageURL());

        return center;
    }

    @Override
    public List<YearData> receiveReports() {
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH);

        List<EEvent> allEEvents = entityManager
                .createQuery("select e from EEvent e where e.dateEvent < :now order by e.dateEvent desc",
                        EEvent.class)
                .setParameter("now", timestamp).getResultList();

        Map<Integer, Map<Integer, List<Event>>> groupedEvents = new HashMap<>();

        List<Event> allEvents = new ArrayList<>();

        for (EEvent e : allEEvents) {
            Event event = new Event();
            event.setEventID(e.getEventID());
            event.setName(e.getNameEvent());
            event.setDescription(e.getDescriptionEvent());
            event.setImageUrl(e.getImage());
            event.setDateC(e.getDateEvent());

            List<EUserEvent> ue = entityManager.createQuery(
                    "SELECT p "
                            + "FROM EUserEvent p "
                            + "where p.fkEventID = :id and p.stampParticipate = true",
                    EUserEvent.class)
                    .setParameter("id", e)
                    .getResultList();

            Integer count = ue != null
                    ? ue.size()
                    : 0;

            event.setNumberParticipants(count);

            allEvents.add(event);
        }

        for (Event event : allEvents) {

            Integer year = event.getDateC().get(Calendar.YEAR);
            Integer month = event.getDateC().get(Calendar.MONTH);

            if (year == currentYear && month == currentMonth) {
                continue;
            }

            groupedEvents
                    .computeIfAbsent(year, k -> new HashMap<>())
                    .computeIfAbsent(month, k -> new ArrayList<>())
                    .add(event);
        }

        List<YearData> result = new ArrayList<>();
        for (Map.Entry<Integer, Map<Integer, List<Event>>> yearEntry : groupedEvents.entrySet()) {
            YearData yearData = new YearData();
            yearData.setYear(yearEntry.getKey());

            List<MonthData> months = new ArrayList<>();

            for (Map.Entry<Integer, List<Event>> monthEntry : yearEntry.getValue().entrySet()) {
                MonthData monthData = new MonthData();
                Integer monthNumber = monthEntry.getKey() + 1;

                monthData.setEvents(monthEntry.getValue());
                monthData.setMonthName(RU_MONTHS[monthEntry.getKey()]);
                monthData.setMonthNumber(monthNumber);
                Logger.getLogger("Проверка вывода monthData.getMonthName(): " + monthData.getMonthName())
                        .info("what happend monthData.getMonthName() = " + monthData.getMonthName());

                months.add(monthData);
            }

            months.sort((y1, y2) -> y2.getMonthNumber() - y1.getMonthNumber());
            yearData.setMonth(months);

            result.add(yearData);
            Logger.getLogger("Проверка вывода yearData.getMonth(): " + yearData.getMonth())
                    .info("what happend yearData.getMonth() = " + yearData.getMonth());

        }

        result.sort((y1, y2) -> y2.getYear() - y1.getYear());

        Logger.getLogger("Проверка вывода result: " + result).info("what happend = " + result);

        return result;
    }
}
