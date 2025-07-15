package com.bash.Unitrack.Repositories;

import com.bash.Unitrack.Data.Models.Session;
import com.bash.Unitrack.Data.Models.Stat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    public List<Session> findByStatus(Stat status);
}
