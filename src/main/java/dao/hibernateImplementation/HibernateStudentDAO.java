package dao.hibernateImplementation;

import dao.interfaces.StudentDAO;
import dto.Course;
import dto.Student;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.hibernate.HibernateException;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class HibernateStudentDAO implements StudentDAO {
    private static EntityManager em;
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("student-manager");
    private static HibernateStudentDAO INSTANCE;
    private static final Logger logger = Logger.getLogger(HibernateStudentDAO.class.getName());


    private HibernateStudentDAO() {
        em = ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public static HibernateStudentDAO getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new HibernateStudentDAO();
        }

        return INSTANCE;
    }

    @Override
    public void createStudent(String firstName, String lastName, int registrationNum) {
        EntityTransaction et = null;
        if(containsStudent(registrationNum)) {
            logger.log(Level.INFO, "Student already exists!");
            return;
        }
        try {
            et = em.getTransaction();
            et.begin();
            Student stud = new Student(firstName, lastName, registrationNum);
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
    public void showAllStudents() {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Student> cq = cb.createQuery(Student.class);
            Root<Student> rootEntry = cq.from(Student.class);
            CriteriaQuery<Student> all = cq.select(rootEntry);

            TypedQuery<Student> allQuery = em.createQuery(all);
            allQuery.getResultList().forEach(stud -> logger.log(Level.INFO, stud.toString()));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void createStudentWithCourses(String firstName, String lastName, int registrationNum, List<Course> courses) {
        if(containsStudent(registrationNum)) {
            logger.log(Level.INFO, "Student already exists");
            return;
        }
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Student stud = new Student(firstName, lastName, registrationNum);
            for(Course course : courses) {
                if(em.find(Course.class, course.getTitle()) == null) {
                    em.persist(course);
                }
                stud.addCourse(course);
            }
            em.persist(stud);
            et.commit();
        } catch (HibernateException ex) {
            if(et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    private boolean containsStudent(int registrationNum) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
        Root<Student> rootEntry = cq.from(Student.class);
        CriteriaQuery<Student> all = cq.select(rootEntry)
                .where(cb.equal(rootEntry.get("registrationNumber"), registrationNum));

        TypedQuery<Student> allQuery = em.createQuery(all);
        if(!allQuery.getResultList().isEmpty()) {
            return true;
        }
        return false;
    }
}
