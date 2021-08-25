package cz.loono.backend.data.repository

import cz.loono.backend.data.model.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {

    @Query(value = "select exists(select 1 from \"user\" where uid = :uid)", nativeQuery = true)
    fun doesUserExist(@Param("uid") uid: String): Boolean
}
