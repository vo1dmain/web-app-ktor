package ru.vo1d.web.data.dao

import ru.vo1d.web.entities.daybook.group.EducationForm
import ru.vo1d.web.entities.daybook.group.GraduationDegree
import ru.vo1d.web.entities.daybook.group.GraduationLevel
import ru.vo1d.web.entities.daybook.group.Group

interface GroupDao : Dao<String, Group>, AllDao<Group>

interface GradLevelDao : Dao<String, GraduationLevel>, AllDao<GraduationLevel>

interface GradDegreeDao : Dao<String, GraduationDegree>, AllDao<GraduationDegree>

interface EduFormDao : Dao<String, EducationForm>, AllDao<EducationForm>