package com.example.lab8;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TVDao {

    // 1. Metoda de inserare in baza de date a unui obiect TV
    @Insert
    void insert(TV tv);

    // 2. Metoda de selectie a tuturor inregistrarilor din tabela
    @Query("SELECT * FROM televizoare")
    List<TV> getAll();

    // 3. Metoda de selectie a obiectului care are valoarea string (marca) egala cu o valoare primita ca parametru
    @Query("SELECT * FROM televizoare WHERE marca = :marca")
    List<TV> getByMarca(String marca);

    // 4. Metoda de selectie a obiectelor care au valoarea intreaga (diagonala) intr-un interval setat de catre doi parametri
    @Query("SELECT * FROM televizoare WHERE diagonala BETWEEN :min AND :max")
    List<TV> getByDiagonalaInterval(int min, int max);

    // 5. Metoda de stergere a inregistrarilor care au o valoare numerica (pret) mai mare decat un parametru
    @Query("DELETE FROM televizoare WHERE pret > :pretMaxim")
    void deleteByPretMaiMare(double pretMaxim);

    // 5b. Metoda de stergere a inregistrarilor care au o valoare numerica (pret) mai mica decat un parametru
    @Query("DELETE FROM televizoare WHERE pret < :pretMinim")
    void deleteByPretMaiMic(double pretMinim);

    // 6. Metoda de crestere cu o unitate a valorii numerice (diagonala) pentru toate inregistrarile
    // a caror valoare de tipul string (marca) incepe cu o litera primita ca parametru
    @Query("UPDATE televizoare SET diagonala = diagonala + 1 WHERE marca LIKE :litera || '%'")
    void incrementDiagonalaByLitera(String litera);

    // Extra: Sterge toate inregistrarile (pentru resetare)
    @Query("DELETE FROM televizoare")
    void deleteAll();
}
