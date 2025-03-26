package com.mycompany.Service;

import org.springframework.stereotype.Service;

import com.mycompany.Model.User;
import com.mycompany.dao.UserDAO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    /*private final UserRepository repo;
    private final UserRepository repo;

    public List<User> listAll() {
        return repo.findAll();
    }

    public void save(User user) {
        repo.save(user);
    }

    public User get(Long id) throws UserNotFoundException {
        Optional<User> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("Could not find any users with ID " + id);
    }

    public void delete(Long id) throws UserNotFoundException {
        Long count = repo.countById(id);
        if (count == null || count == 0) {
            throw new UserNotFoundException("Could not find any users with ID " + id);
        }
        repo.deleteById(id);
    }*/

    private final UserDAO userDAO;

    public List<User> listAll() {
        return userDAO.listAll();
    }

    public User save(User user) {
        return userDAO.save(user);
    }

    public User get(Long id) throws UserNotFoundException {
        Optional<User> result = userDAO.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("Could not find any users with ID " + id);
    }

    @Transactional
    public User getUserById(Long id) {
        User result = userDAO.getUserById(id);
        return result;
    }

    public Optional<User> getByEmail(String email) {
        return userDAO.findByEmail(email);  
    }

    public void delete(Long id) throws UserNotFoundException {
        Long count = userDAO.countById(id);
        if (count == null || count == 0) {
            throw new UserNotFoundException("Could not find any users with ID " + id);
        }
        userDAO.deleteById(id);
    }
}
