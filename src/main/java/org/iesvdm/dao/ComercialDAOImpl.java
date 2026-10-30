package org.iesvdm.dao;

import java.util.List;
import java.util.Optional;
import org.iesvdm.modelo.Comercial;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ComercialDAOImpl implements ComercialDAO {

    private final JdbcTemplate jdbcTemplate;

    ComercialDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public synchronized void create(Comercial comercial) {
        String sqlInsert = "INSERT INTO comercial (nombre, apellido1, apellido2, comisión) VALUES (?, ?, ?, ?)";
        int rows = jdbcTemplate.update(sqlInsert, 
                comercial.getNombre(), 
                comercial.getApellido1(), 
                comercial.getApellido2(), 
                comercial.getComisión());
        log.info("Insertados {} registros de comercial.", rows);
    }

    @Override
    public List<Comercial> getAll() {
        List<Comercial> listComerciales = jdbcTemplate.query(
            "SELECT * FROM comercial",
            (rs, rowNum) -> new Comercial(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellido1"),
                rs.getString("apellido2"),
                rs.getFloat("comisión")
            )
        );
        log.info("Devueltos {} comerciales.", listComerciales.size());
        return listComerciales;
    }

    @Override
    public Optional<Comercial> find(int id) {
        try {
            Comercial comercial = jdbcTemplate.queryForObject(
                "SELECT * FROM comercial WHERE id = ?",
                (rs, rowNum) -> new Comercial(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido1"),
                    rs.getString("apellido2"),
                    rs.getFloat("comisión")
                ),
                id
            );
            return Optional.of(comercial);
        } catch (Exception e) {
            log.info("Comercial con id {} no encontrado.", id);
            return Optional.empty();
        }
    }

    @Override
    public void update(Comercial comercial) {
        int rows = jdbcTemplate.update("""
            UPDATE comercial SET
            nombre = ?,
            apellido1 = ?,
            apellido2 = ?,
            comisión = ?
            WHERE id = ?
            """,
            comercial.getNombre(),
            comercial.getApellido1(),
            comercial.getApellido2(),
            comercial.getComisión(),
            comercial.getId()
        );
        log.info("Update de Comercial con {} registros actualizados.", rows);
    }


    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }}