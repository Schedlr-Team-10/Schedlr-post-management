package com.schedlr.Schedlr_post_management;

import com.schedlr.Schedlr_post_management.Entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class SchedlrPostManagementApplication{
	public static void main(String[] args) {
		SpringApplication.run(SchedlrPostManagementApplication.class, args);
	}
}
