package com.schedlr.Schedlr_post_management.repo;
import com.schedlr.Schedlr_post_management.Entity.SchedulePostUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulePostUploadRepository  extends JpaRepository<SchedulePostUpload,Integer> {
}