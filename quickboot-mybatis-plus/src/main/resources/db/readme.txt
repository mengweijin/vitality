flyway在spring boot中默认配置位置为：classpath:db/migration
flyway命名规则为：V<VERSION>__<NAME>.sql (with <VERSION> an underscore-separated version, such as ‘1’ or ‘2_1’)