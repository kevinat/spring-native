package org.springframework.boot.autoconfigure.data.neo4j;

import org.springframework.data.neo4j.config.AbstractNeo4jConfig;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.mapping.callback.AuditingBeforeBindCallback;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;
import org.springframework.data.neo4j.repository.config.Neo4jRepositoryConfigurationExtension;
import org.springframework.data.neo4j.repository.support.Neo4jEvaluationContextExtension;
import org.springframework.data.neo4j.repository.support.Neo4jRepositoryFactoryBean;
import org.springframework.data.neo4j.repository.support.SimpleNeo4jRepository;
import org.springframework.nativex.type.NativeConfiguration;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.ProxyHint;
import org.springframework.nativex.hint.TypeHint;
import org.springframework.nativex.hint.AccessBits;

@NativeHint(trigger = Neo4jRepositoriesAutoConfiguration.class, types = {
	@TypeHint(types = {
		org.springframework.data.neo4j.core.mapping.callback.BeforeBindCallback.class,
		org.springframework.data.neo4j.repository.event.BeforeBindCallback.class,
	}, access=AccessBits.CLASS|AccessBits.DECLARED_METHODS),
	@TypeHint(types = {
		Neo4jRepositoryFactoryBean.class,
		Neo4jRepositoryConfigurationExtension.class,
		AbstractNeo4jConfig.class,
		SimpleNeo4jRepository.class,
		Neo4jTemplate.class,
		Neo4jClient.class,
		AuditingBeforeBindCallback.class,
		UUIDStringGenerator.class,
		Neo4jEvaluationContextExtension.class
	},
		typeNames = {
			"org.springframework.data.neo4j.repository.query.SimpleQueryByExampleExecutor",
			"org.springframework.data.neo4j.repository.query.SimpleReactiveQueryByExampleExecutor",
			"org.springframework.data.neo4j.core.schema.GeneratedValue$InternalIdGenerator",
			"org.springframework.data.neo4j.core.schema.GeneratedValue$UUIDGenerator",
			"org.springframework.data.neo4j.core.mapping.callback.IdGeneratingBeforeBindCallback",
			"org.springframework.data.neo4j.core.mapping.callback.OptimisticLockingSupport"
		}
	) },
	proxies = {
		@ProxyHint(typeNames = {
			"org.springframework.data.annotation.Id",
			"org.springframework.core.annotation.SynthesizedAnnotation"
		})
	})
public class Neo4jRepositoriesHints implements NativeConfiguration {

}
