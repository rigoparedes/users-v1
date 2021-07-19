package com.mycompany.users.adapter.ouput.database.repository;

import com.mycompany.users.adapter.ouput.database.model.UserDb;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserDb, UUID> {

}
