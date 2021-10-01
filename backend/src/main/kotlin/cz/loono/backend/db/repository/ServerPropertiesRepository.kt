package cz.loono.backend.db.repository

import cz.loono.backend.db.model.ServerProperties
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ServerPropertiesRepository : CrudRepository<ServerProperties, Long> {

    override fun findAll(): List<ServerProperties>

    @Query("select sp.super_user_name as superUserName, sp.super_user_password as superUserPassword from server_properties sp", nativeQuery = true)
    fun findAllSuperUserNameAndPassword(): List<SuperUser>

    @Query("select sp.update_interval as updateInterval from server_properties sp", nativeQuery = true)
    fun findAllUpdateInterval(): List<OpenDataProperties>
}

interface SuperUser {
    fun getSuperUserName(): String
    fun getSuperUserPassword(): String
}

interface OpenDataProperties {
    fun getUpdateInterval(): String
}
