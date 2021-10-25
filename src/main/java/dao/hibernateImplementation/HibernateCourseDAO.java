package dao.hibernateImplementation;

import dao.interfaces.CourseDAO;
import dto.Course;
import dto.Student;
import org.hibernate.HibernateException;
import java.util.logging.Level;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.logging.Logger;

public class HibernateCourseDAO implements CourseDAO {
    private static EntityManager em;
    private static HibernateCourseDAO INSTANCE;
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("student-manager");
    private static final Logger logger = Logger.getLogger(HibernateCourseDAO.class.getName());


    private HibernateCourseDAO() {
        em = ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public static HibernateCourseDAO getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new HibernateCourseDAO();
        }

        return INSTANCE;
    }

    @Override
    public void addCourse(int registrationNum, String courseTitle, String description) {
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Course course = new Course(courseTitle, description, registrationNum);
            Student stud = em.find(Student.class, registrationNum);
            stud.addCourse(course);
            if(!containsCourse(courseTitle)) {
                em.persist(course);
            }
            em.persist(stud);
            et.commit();
        } catch (HibernateException ex) {
            if(et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void printCourses(int registrationNum) {
        try {
            Student stud = em.find(Student.class, registrationNum);
            stud.getCourseSet().forEach(course -> logger.log(Level.INFO, course.toString()));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    private boolean containsCourse(String title) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cq = cb.createQuery(Course.class);
        Root<Course> rootEntry = cq.from(Course.class);
        CriteriaQuery<Course> all = cq.select(rootEntry)
                .where(cb.equal(rootEntry.get("title"), title));

        TypedQuery<Course> allQuery = em.createQuery(all);
        if(!allQuery.getResultList().isEmpty()) {
            return true;
        }
        return false;
    }
}