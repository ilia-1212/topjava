package ru.javawebinar.topjava.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m " +
                "WHERE m.id=:id and m.user.id=:user_id"),
        @NamedQuery(name = Meal.UPDATE, query = "UPDATE Meal m " +
                "SET m.description=:description, m.calories=:calories, m.dateTime=:date_time " +
                "WHERE m.id=:id and m.user.id=:user_id"),
        @NamedQuery(name = Meal.BY_ID_USER_ID, query = "SELECT m FROM Meal m " +
                "WHERE m.id=:id and m.user.id=:user_id"),
        @NamedQuery(name = Meal.ALL_SORTED, query = "SELECT m FROM Meal m " +
                "WHERE m.user.id=:user_id ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.BTW_DATETIME_SORTED, query = "SELECT m FROM Meal m " +
                "WHERE m.user.id=?1  AND m.dateTime >=?2 AND m.dateTime <?3 ORDER BY m.dateTime DESC")
})
@Entity
@Table(name = "meal", uniqueConstraints = @UniqueConstraint(name = "meal_unique_user_datetime_idx", columnNames = {"user_id", "date_time"}))
public class Meal extends AbstractBaseEntity {
    public static final String DELETE = "meal.delete";
    public static final String UPDATE = "meal.update";
    public static final String BY_ID_USER_ID = "meal.getById_User_Id";
    public static final String ALL_SORTED = "meal.getAllSorted";
    public static final String BTW_DATETIME_SORTED = "meal.getHalfOpenSorted";

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @Size(max = 255)
    @NotBlank
    private String description;

    @Column(name = "calories", nullable = false)
    @Range(min = 0, max = 10000)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "meal_user_id_fkey"), insertable = true, updatable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
