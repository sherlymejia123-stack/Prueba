/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.holamundo_3periodo;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
/**
 *
 * @author Nitro
 */
public class HolaMundo_3Periodo {

    public static void main(String[] args) throws SQLException {
        System.out.println("==Ejecutando Operaciones en tabla Alumnos==");
        //Obtener la conexion
        Connection cn = ConexionDB.getConexion();
        if(cn == null){
            System.out.println("No se pude establecer la conexion");
            return;
        }
        //Declarar los objetos de SQL para nuestra base
        PreparedStatement psInsertar = null;
        PreparedStatement psSeleccionar = null;
        PreparedStatement psActualizar = null; 
        PreparedStatement psEliminar = null;
        ResultSet rs = null;
        try{
            System.out.println("\n 1. Intentando insertar nuevo Alumno....");
            String psInsert = "INSERT INTO Alumnos(nombre, correo) VALUES(?,?)";
            psInsertar = cn.prepareStatement(psInsert);
            
            //asignaremos valores a nuetros parametros
           psInsertar.setString(1, "Sara Mejia");
           psInsertar.setString(2, "Sara.mejia@uth.hn");
           //ejecutar la insercion
           int filasAfectadas = psInsertar.executeUpdate();
           if (filasAfectadas > 0){
               System.out.println("Alumno insertado con exito");
           }
           
           //Actualizar alumnos
           System.out.println("\n2. Intentando actualizar un Alumno....");
           String sqlActualizar = "UPDATE Alumnos SET nombre = ?, correo = ? WHERE id = ?";
           psActualizar = cn.prepareStatement(sqlActualizar);
           
           //Alumno que se quiere actualizar
           psActualizar.setString(1, "Pedro Infante"); 
           psActualizar.setString(2, "pedro.infante@uth.hn"); 
           psActualizar.setInt(3, 12);
           
           // Ejecutar actualizacion
           int filasActualizadas = psActualizar.executeUpdate();
           if (filasActualizadas > 0){
               System.out.println("Alumno actualizado con exito"); 
           }else{ 
               System.out.println("No se encontro el alumno para actualizar."); 
           }
           
           //Eliminar alumno
           System.out.println("\n3. Intentando eliminar un Alumno....");
           String sqlEliminar = "DELETE FROM Alumnos WHERE id = ?";
           psEliminar = cn.prepareStatement(sqlEliminar);
           
           psEliminar.setInt(1,10);
           int filasEliminadas = psEliminar.executeUpdate();
           
           if (filasEliminadas > 0){ 
               System.out.println("Alumno eliminado con exito"); 
           }else{ 
               System.out.println("No se encontro el alumno para eliminar."); 
           }
           
           //Parte B seleccionar y visualizar datos en pantalla
           System.out.println("\n 2. Consulta de lista de Alumnos en la Base de Datos");
           String sqlSelect = "SELECT id, nombre, correo FROM Alumnos";
           psSeleccionar = cn.prepareStatement(sqlSelect);
           rs = psSeleccionar.executeQuery();
           System.out.println("\n------------------------------------------------------");
           System.out.println("  ID      |Nombre               |Correo                 ");
           System.out.println("--------------------------------------------------------");
           while(rs.next()){
               int id = rs.getInt("id");
               String nombre = rs.getString("nombre");
               String correo = rs.getString("correo");
               //Imprimimos la tabla virtual
               System.out.printf("%-3d | %-20s |%s\n", id, nombre, correo);
           }
           System.out.println("--------------------------------------------------------");
        }catch(SQLException e){
            System.err.println("Ocurrio un error con la base de datos" + e.getMessage());
        }finally{
            //cerrar la conexion de manera obligatoria 
            if(rs != null){
                rs.close();   
            }
            if(psInsertar != null)psInsertar.close();
            if(psSeleccionar != null)psSeleccionar.close();
            if(psEliminar != null)psEliminar.close();
            if(psSeleccionar != null)psSeleccionar.close();
            ConexionDB.cerrarConexion();
        }
    }       
}
