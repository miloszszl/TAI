package com.milosz.tai.app.Entities.Parameters;

public class ConstraintValues {
    //user
    public static final int USER_NICKNAME_LENGTH = 25;
    public static final int USER_EMAIL_LENGTH = 50;
    public static final int USER_HASHED_PASSWORD_LENGTH = 184;
    public static final int USER_SALT_LENGTH = 10;

    //userRole
    public static final int USER_ROLE_NAME_LENGTH = 20;

    //movieType
    public static final int MOVIE_TYPE_NAME_LENGTH = 30;

    //movie
    public static final int MOVIE_TITLE_LENGTH = 50;
    public static final int MOVIE_DESCRIPTION_LENGTH = 1000;

    //comment
    public static final int COMMENT_CONTENT_LENGTH = 500;

    //moviePerson
    public static final int MOVIE_PERSON_NAME_LENGTH = 30;
    public static final int MOVIE_PERSON_SURNAME_LENGTH = 30;
    public static final int MOVIE_PERSON_BIRTH_PLACE_LENGTH = 100;

    //MovieMoviePerson
    public static final int MOVIE_MOVIE_PERSON_ROLE_NAME_LENGTH = 100;

}
