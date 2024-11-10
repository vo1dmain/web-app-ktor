package ru.vo1d.web.data.dao

import ru.vo1d.web.entities.daybook.group.Group
import ru.vo1d.web.entities.daybook.group.degree.GraduationDegree
import ru.vo1d.web.entities.daybook.group.form.EducationForm
import ru.vo1d.web.entities.daybook.group.level.GraduationLevel

interface GroupDao : Dao<String, Group>, AllDao<Group>

interface GradLevelDao : Dao<String, GraduationLevel>, AllDao<GraduationLevel>

interface GradDegreeDao : Dao<String, GraduationDegree>, AllDao<GraduationDegree>

interface EduFormDao : Dao<String, EducationForm>, AllDao<EducationForm>