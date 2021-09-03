package cz.loono.backend.db.repository

import cz.loono.backend.db.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {

    fun existsByUid(uid: String): Boolean
}
