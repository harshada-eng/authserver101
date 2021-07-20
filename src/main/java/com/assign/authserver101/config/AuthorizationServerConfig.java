package com.assign.authserver101.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer                                                                         // to enable the functionality of authorization server
@EnableResourceServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {              // seperated this class intentionally since it has seperate responsibility & need to inherit another class here

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {               // in this method we can add our clients..
                                                                                                   // for eg. we register our application with github..so we gain back client Id & secret...here we are coding the client
                                                                                                   // need to know who are your clients that can attempt to connect & use your users
                 clients.inMemory()                                                                // overriding configure method to specify who are the clients & applications that can use the responsibility of our authorization server
                        .withClient("clientId")                                             // clientId is the userName of client...we gain clientId  when we registered our application with git hub in order to attempt to use the users of github in sso application
                        .secret("abcde")
                        .authorizedGrantTypes("password", "refresh_token", "authorization_token")   // to be allowed to generate & refresh the tokens
                        .scopes("write");                                                           // authorities....need to mention at least one scope requested by an application
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {     // it's an object that can be used to customize authorization endpoint
        endpoints.authenticationManager(authenticationManager);                                    // need to specify object of a type authenticationManager by declaring it as a private & autowiring it
                                                                                                   // this object can be used to send as a parameters to the authentication manager of the endpoint object method
    }
}
