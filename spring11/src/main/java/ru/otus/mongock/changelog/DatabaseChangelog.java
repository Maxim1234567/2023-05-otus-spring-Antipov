package ru.otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.domain.Passport;
import ru.otus.repository.PassportRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {
    private final List<Passport> passports = List.of(
            Passport.builder().id("1").series("0000").number("111111").issued("there").dateOfIssue("00.00.0000").codeDivision("000-001").build(),
            Passport.builder().id("2").series("0001").number("111110").issued("there").dateOfIssue("00.00.1000").codeDivision("000-010").build(),
            Passport.builder().id("3").series("0010").number("111100").issued("where").dateOfIssue("00.00.2000").codeDivision("000-100").build(),
            Passport.builder().id("4").series("0011").number("111000").issued("here").dateOfIssue("00.00.3000").codeDivision("001-000").build(),
            Passport.builder().id("5").series("0100").number("110000").issued("here").dateOfIssue("00.00.4000").codeDivision("010-000").build()
    );

    @ChangeSet(order = "001", id ="dropDB", author = "antipov", runAlways = true)
    public void dropDB(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertPassport1", author = "antipov")
    public void insertPassport1(PassportRepository passportRepository) {
        passportRepository.save(passports.get(0)).subscribe();
    }

    @ChangeSet(order = "003", id = "insertPassport2", author = "antipov")
    public void insertPassport2(PassportRepository passportRepository) {
        passportRepository.save(passports.get(1)).subscribe();
    }

    @ChangeSet(order = "004", id = "insertPassport3", author = "antipov")
    public void insertPassport3(PassportRepository passportRepository) {
        passportRepository.save(passports.get(2)).subscribe();
    }

    @ChangeSet(order = "005", id = "insertPassport4", author = "antipov")
    public void insertPassport4(PassportRepository passportRepository) {
        passportRepository.save(passports.get(3)).subscribe();
    }

    @ChangeSet(order = "006", id = "insertPassport5", author = "antipov")
    public void insertPassport5(PassportRepository passportRepository) {
        passportRepository.save(passports.get(4)).subscribe();
    }
}
