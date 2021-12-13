package ro.unibuc.springlab8example1.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ro.unibuc.springlab8example1.domain.User;
import ro.unibuc.springlab8example1.domain.UserDetails;
import ro.unibuc.springlab8example1.domain.UserType;
import ro.unibuc.springlab8example1.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User save(User user) {
        String saveUserSql = "INSERT INTO users (username, full_name, user_type, account_created) VALUES (?,?,?,?)";
        jdbcTemplate.update(saveUserSql, user.getUsername(), user.getFullName(), user.getUserType().name(), LocalDateTime.now());

        User savedUser = getUserWith(user.getUsername());
        UserDetails userDetails = user.getUserDetails();

        if (userDetails != null) {
            String saveUserDetailsSql = "INSERT INTO user_details (cnp, age, other_information) VALUES (?, ?, ?)";
            jdbcTemplate.update(saveUserDetailsSql, userDetails.getCnp(), userDetails.getAge(), userDetails.getOtherInformation());

            UserDetails savedUserDetails = getUserDetailsWith(userDetails.getCnp());
            savedUser.setUserDetails(savedUserDetails);

            String saveUsersUserDetails = "INSERT INTO users_user_details (users, user_details) VALUES (?, ?)";
            jdbcTemplate.update(saveUsersUserDetails, savedUser.getId(), savedUserDetails.getId());
        }

        return savedUser;
    }

    public void deleteById(Long id) {
        String saveUserSql = "delete from users where id = ?";

        User savedUser = getUserById(id);
        UserDetails userDetails = savedUser.getUserDetails();

        if (userDetails != null) {
            String saveUserDetailsSql = "delete from user_details where id = ?";

            UserDetails savedUserDetails = getUserDetailsById(userDetails.getId());
            savedUser.setUserDetails(savedUserDetails);

            String saveUsersUserDetails = "delete from users_user_details where id = ?";
            jdbcTemplate.update(saveUsersUserDetails, savedUser.getId());

            jdbcTemplate.update(saveUserDetailsSql, userDetails.getId());

        }

        jdbcTemplate.update(saveUserSql, id);

    }

    public User updateUser(User user) {
        String saveUserSql = "update users set username = ?, full_name = ?, user_type = ?, account_created = ? where id=?";
        jdbcTemplate.update(saveUserSql, user.getUsername(), user.getFullName(), user.getUserType().name(), LocalDateTime.now(), user.getId());

        User savedUser = getUserById(user.getId());
        UserDetails userDetails = savedUser.getUserDetails();

        if (userDetails != null) {
            String saveUserDetailsSql = "update user_details set cnp = ?, age = ?, other_information = ? where id=?";
            jdbcTemplate.update(saveUserDetailsSql, user.getUserDetails().getCnp(), user.getUserDetails().getAge(), user.getUserDetails().getOtherInformation(), userDetails.getId());

            System.out.println("IDDDDDDD" + userDetails.getId());
            UserDetails savedUserDetails = getUserDetailsById(userDetails.getId());
            savedUser.setUserDetails(savedUserDetails);

        }

        return savedUser;
    }

    public User get(String username) {
        // TODO : homework: use JOIN to fetch all details about the user
        String selectSql = "SELECT u.id,u.full_name,u.username, udi.cnp, u.user_type, udi.age, udi.other_information FROM users u join users_user_details ud \n" +
                "on u.id = ud.users \n" +
                "join user_details udi \n" +
                "on ud.user_details=udi.id WHERE u.username = ?";
        RowMapper<User> rowMapper = (resultSet, rowNo) -> User.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .fullName(resultSet.getString("full_name"))
                .userDetails(new UserDetails(resultSet.getLong("id"),resultSet.getString("cnp"),
                        resultSet.getInt("age"), resultSet.getString("other_information")))
//                .userDetails.cnp(resultSet.getString())
                .userType(UserType.valueOf(resultSet.getString("user_type")))
                .build();

        List<User> users = jdbcTemplate.query(selectSql, rowMapper, username);

        if (!users.isEmpty()) {
            return users.get(0);
        }

        throw new UserNotFoundException("User not found");
    }

    public List<User> getType(String type) {
        // TODO : homework: use JOIN to fetch all details about the user
        String selectSql = "SELECT u.id,u.full_name,u.username, udi.cnp, u.user_type, udi.age, udi.other_information FROM users u join users_user_details ud \n" +
                "on u.id = ud.users \n" +
                "join user_details udi \n" +
                "on ud.user_details=udi.id WHERE u.user_type = ?";
        RowMapper<User> rowMapper = (resultSet, rowNo) -> User.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .fullName(resultSet.getString("full_name"))
                .userDetails(new UserDetails(resultSet.getLong("id"),resultSet.getString("cnp"),
                        resultSet.getInt("age"), resultSet.getString("other_information")))
//                .userDetails.cnp(resultSet.getString())
                .userType(UserType.valueOf(resultSet.getString("user_type")))
                .build();

        List<User> users = jdbcTemplate.query(selectSql, rowMapper, type);

        if (!users.isEmpty()) {
            return users;
        }

        throw new UserNotFoundException("User type not found");
    }

    private User getUserById(Long id) {
        String selectSql = "SELECT u.id,u.full_name,u.username, udi.cnp, u.user_type, udi.age, udi.other_information FROM users u join users_user_details ud \n" +
                "on u.id = ud.users \n" +
                "join user_details udi \n" +
                "on ud.user_details=udi.id WHERE u.id = ?";
        RowMapper<User> rowMapper = (resultSet, rowNo) -> User.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .fullName(resultSet.getString("full_name"))
                .userDetails(new UserDetails(resultSet.getLong("id"),resultSet.getString("cnp"),
                        resultSet.getInt("age"), resultSet.getString("other_information")))
                .userType(UserType.valueOf(resultSet.getString("user_type")))
                .build();

        List<User> users = jdbcTemplate.query(selectSql, rowMapper, id);

        if (!users.isEmpty()) {
            return users.get(0);
        }

        throw new UserNotFoundException("User by id not found");
    }


    private User getUserWith(String username) {
        String selectSql = "SELECT * from users WHERE users.username = ?";
        RowMapper<User> rowMapper = (resultSet, rowNo) -> User.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .fullName(resultSet.getString("full_name"))
                .userType(UserType.valueOf(resultSet.getString("user_type")))
                .build();

        List<User> users = jdbcTemplate.query(selectSql, rowMapper, username);

        if (!users.isEmpty()) {
            return users.get(0);
        }

        throw new UserNotFoundException("User not found");
    }

    private UserDetails getUserDetailsById(Long id) {
        String selectSql = "SELECT * from user_details WHERE user_details.id = ?";
        RowMapper<UserDetails> rowMapper = (resultSet, rowNo) -> UserDetails.builder()
                .id(resultSet.getLong("id"))
                .cnp(resultSet.getString("cnp"))
                .age(resultSet.getInt("age"))
                .otherInformation(resultSet.getString("other_information"))
                .build();

        List<UserDetails> details = jdbcTemplate.query(selectSql, rowMapper, id);

        if (!details.isEmpty()) {
            return details.get(0);
        }

        throw new UserNotFoundException("User details by id not found");
    }

    private UserDetails getUserDetailsWith(String cnp) {
        String selectSql = "SELECT * from user_details WHERE user_details.cnp = ?";
        RowMapper<UserDetails> rowMapper = (resultSet, rowNo) -> UserDetails.builder()
                .id(resultSet.getLong("id"))
                .cnp(resultSet.getString("cnp"))
                .age(resultSet.getInt("age"))
                .otherInformation(resultSet.getString("other_information"))
                .build();

        List<UserDetails> details = jdbcTemplate.query(selectSql, rowMapper, cnp);

        if (!details.isEmpty()) {
            return details.get(0);
        }

        throw new UserNotFoundException("User details not found");
    }
}
