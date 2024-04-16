import Exceptions.*;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable {


    private String Author;
    private String Title;
    private String Genre;
    private String Resume;
    private int RealiseYear;
    private ArrayList<String> KeyWords =new ArrayList<>();
    private double Rating;
    private String UniqueNumber;
    public Book(String Title, String Author, String Genre, String Resume, int RealiseYear, ArrayList<String> KeyWords,double Rating, String UniqueNumber) throws Exception{
        setAuthor(Author);
        setTitle(Title);
        setGenre(Genre);
        setResume(Resume);
        setRealiseYear(RealiseYear);
        setKeyWords(KeyWords);
        setRating(Rating);
        setUniqueNumber(UniqueNumber);
    }
    public String getAuthor() {
        return Author;
    }

    public String getTitle() {
        return Title;
    }

    public String getGenre() {
        return Genre;
    }

    public String getResume() {
        return Resume;
    }

    public int getRealiseYear() {
        return RealiseYear;
    }


    public ArrayList<String> getKeyWords() {
        return KeyWords;
    }


    public double getRating() {
        return Rating;
    }


    public String getUniqueNumber() {
        return UniqueNumber;
    }


    public void setAuthor(String Author) throws InvalidAuthorException {
        if (Author == null || Author.isEmpty()) {
            throw new InvalidAuthorException("Author cannot be null or empty");
        }
        this.Author = Author;
    }

    public void setTitle(String Title) throws InvalidTitleException {
        if (Title == null || Title.isEmpty()) {
            throw new InvalidTitleException("Title cannot be null or empty");
        }
        this.Title = Title;
    }


    public void setKeyWords(ArrayList<String> KeyWords) throws InvalidKeyWordsException {
        if (KeyWords == null || KeyWords.isEmpty()) {
            throw new InvalidKeyWordsException("Key words cannot be null or empty");
        }
        this.KeyWords = KeyWords;
    }

    public void setRating(double Rating) throws InvalidRatingException {
        if (Rating < 0 || Rating > 10) {
            throw new InvalidRatingException("Rating must be between 0 and 10");
        }
        this.Rating = Rating;
    }

    public void setGenre(String Genre) throws InvalidGenreException {

        if (Genre == null || Genre.isEmpty()) {
            throw new InvalidGenreException("Genre cannot be null or empty");
        }
        this.Genre = Genre;
    }

    public void setResume(String Resume) throws InvalidResumeException {

        if (Resume == null || Resume.isEmpty()) {
            throw new InvalidResumeException("Resume cannot be null or empty");
        }
        this.Resume = Resume;
    }

    public void setRealiseYear(int RealiseYear) throws InvalidReleaseYearException {

        if (RealiseYear < 0) {
            throw new InvalidReleaseYearException("Release year cannot be negative");
        }

        this.RealiseYear = RealiseYear;
    }

    public void setUniqueNumber(String UniqueNumber) throws InvalidUniqueNumberException {
        // Add validation logic for unique number if needed
        if (UniqueNumber == null || UniqueNumber.isEmpty()) {
            throw new InvalidUniqueNumberException("Unique number cannot be null or empty");
        }
        this.UniqueNumber = UniqueNumber;
    }
}

