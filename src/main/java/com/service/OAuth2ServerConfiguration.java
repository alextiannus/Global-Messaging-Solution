/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class OAuth2ServerConfiguration {

	private static final String RESOURCE_ID = "restservice";

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			// @formatter:off
			resources
				.resourceId(RESOURCE_ID);
			// @formatter:on
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off

			http
			   .authorizeRequests()
			     //用户
					.antMatchers("/oauth/token","/gmtoken","/login","/send/**","/ez/**").permitAll()
					.anyRequest().authenticated();
			// @formatter:on

		}
	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

//		@Autowired
//		private DataSource dataSource;

		@Bean
		public TokenStore tokenStore(){
			return new InMemoryTokenStore();
		}

		@SuppressWarnings("SpringJavaAutowiringInspection")
		@Autowired
		private  AuthenticationManager authenticationManager;

		@SuppressWarnings("SpringJavaAutowiringInspection")
		@Autowired
		private  UserDetailService userDetailService;

		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {

			/**
			 * allow表示允许在认证的时候把参数放到url之中传过去
			 * @see org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter
			 * @see OAuth2AuthenticationProcessingFilter
			 */
			oauthServer.allowFormAuthenticationForClients();
		}
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

//			tokenStore = new JdbcTokenStore(dataSource);
//			tokenStore = new InMemoryTokenStore();
			// @formatter:off
			endpoints
				.tokenStore(tokenStore())

				.authenticationManager(authenticationManager)
				.userDetailsService(userDetailService);
			// @formatter:on

			/*
             * .pathMapping("/oauth/authorize", "/oauth2/authorize")
             * .pathMapping("/oauth/token", "/oauth2/token");
             */
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			// @formatter:off
			clients
				.inMemory()

					.withClient("appclient")
						.authorizedGrantTypes("password", "refresh_token")
						.authorities("USER")
						.scopes("read", "write")
						.resourceIds(RESOURCE_ID)
						.secret("123456");

			// @formatter:on
		}

		@Bean
		@Primary
		public DefaultTokenServices tokenServices() {
			DefaultTokenServices tokenServices = new DefaultTokenServices();
			tokenServices.setSupportRefreshToken(true);
			tokenServices.setTokenStore(tokenStore());
			return tokenServices;
		}

	}

}
