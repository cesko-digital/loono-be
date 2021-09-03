package cz.loono.backend.db.repository

import cz.loono.backend.db.model.Category
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : CrudRepository<Category, String>
