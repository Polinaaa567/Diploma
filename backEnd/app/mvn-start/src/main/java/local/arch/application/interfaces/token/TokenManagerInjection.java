package local.arch.application.interfaces.token;

import local.arch.infrastructure.token.ITokenKey;

public interface TokenManagerInjection {
    public void injectTokenManager(ITokenKey manager);
}