package com.github.mengweijin.woodenman.generator.maven;

import lombok.Data;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
@Data
public class MavenCoordinate {

    private String groupId;

    private String artifactId;

    public MavenCoordinate(){}

    public MavenCoordinate(String groupId, String artifactId){
        this.groupId = groupId;
        this.artifactId = artifactId;
    }
}
