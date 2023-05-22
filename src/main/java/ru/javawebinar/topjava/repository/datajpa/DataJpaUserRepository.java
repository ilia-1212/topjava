package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;

@Repository
@Profile(Profiles.DATAJPA)
public class DataJpaUserRepository implements UserRepository {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    private final CrudUserRepository crudUserRepository;

    public DataJpaUserRepository(CrudUserRepository crudUserRepository) {
        this.crudUserRepository = crudUserRepository;
        ;
    }

    @Override
    public User save(User user) {
        return crudUserRepository.save(user);
    }

    @Override
    public boolean delete(int id) {
        return crudUserRepository.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return crudUserRepository.findById(id).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return crudUserRepository.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return crudUserRepository.findAll(SORT_NAME_EMAIL);
    }

    @Transactional(readOnly = true)
    public User getUserAndHisMeal(int id) {
//        User user = get(id);
//        user.setMeals(crudMealRepository.getAll(id));
        return crudUserRepository.getUserAndHisMeal(id);
    }
}