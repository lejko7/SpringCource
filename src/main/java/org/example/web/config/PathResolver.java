package org.example.web.config;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

@Component
@AllArgsConstructor
public class PathResolver extends PathResourceResolver {

    NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    protected boolean checkResource(Resource resource, Resource location) throws IOException {
        String absolutePath = resource.getFile().getAbsolutePath();
        if (!absolutePath.contains(System.getProperty("catalina.home"))) {
            return false;
        }

        List<String> allowedPaths = jdbcTemplate.query("select * from files", (ResultSet rs, int rowNum) -> rs.getString("filePath"));

        if (!checkIfContains(allowedPaths, resource.getFile().getName())) {
            return false;
        }

        return super.checkResource(resource, location);
    }

    private boolean checkIfContains(List<String> allowedPaths, String name) {

        for (String path : allowedPaths){
            if (path.contains(name)){
                return true;
            }
        }

        return false;
    }
}
