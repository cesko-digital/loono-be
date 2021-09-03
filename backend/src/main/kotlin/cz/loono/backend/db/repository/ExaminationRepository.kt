package cz.loono.backend.db.repository

import cz.loono.backend.db.model.Examination
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ExaminationRepository : CrudRepository<Examination, Long>
