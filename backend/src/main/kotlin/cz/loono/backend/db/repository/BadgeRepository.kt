package cz.loono.backend.db.repository

import cz.loono.backend.db.model.Badge
import cz.loono.backend.db.model.BadgeCompositeKey
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BadgeRepository : CrudRepository<Badge, BadgeCompositeKey>
