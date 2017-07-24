package com.repository;

import com.Entity.sentLog.SendLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Donnie on 2017/2/21.
 */
@Repository
public interface LogRepository extends JpaRepository<SendLog, Long> {
}
