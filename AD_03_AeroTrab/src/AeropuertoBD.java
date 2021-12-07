import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.mysql.cj.xdevapi.Statement;

public class AeropuertoBD {

		static Connection conexion = null;
		static java.sql.Statement stm = null;
		static ResultSet rs = null;
		static Scanner entrada = new Scanner(System.in);
		
	public static void main(String[] args) {
		final String URL = "jdbc:mysql://localhost/ad_tarea3";
		final String user ="root";
		final String pass="";

		int opcion;
		
		
		// Intentamos conexi�n a BD
		
			try {
				conexion = DriverManager.getConnection(URL, user, pass);
				System.out.println("Conectado a BD");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		// Men� de opciones
			
			System.out.println("Introduce la opci�n a realizar:\n"
					+ "1. Informaci�n de los vuelos con destino Madrid.\r\n"
					+ "2. N�mero de plazas de fumador del vuelo Madrid-Roma.\r\n"
					+ "3. Ver la informaci�n de los pasajeros de un vuelo, pasando el c�digo de vuelo como par�metro.\r\n"
					+ "4. Insertar un vuelo cuyos valores se pasan como par�metros.\r\n"
					+ "5. Borrar el vuelo que se meti� anteriormente en el que se pasa por par�metro su n�mero de vuelo.\r\n"
					+ "6. Modificar los vuelos de fumadores a no fumadores.\n");
			System.out.println("Introduce opci�n deseada:\n");
			opcion = entrada.nextInt();
			
			switch (opcion) {
			case 0: 
				System.out.println("Adi�s");
				break;
			case 1:
				System.out.println("Informaci�n de los vuelos con destino Madrid.\n");
				destinoMadrid();
				break;
			case 2:
				System.out.println("N�mero de plazas de fumador del vuelo Madrid-Roma.");
				fumadoresVuelo();
				break;
			case 3:
				System.out.println("Informaci�n de los pasajeros de un vuelo");
				infoPasajeros();
				break;
			case 4:
				System.out.println(" Insertar un vuelo.");
				insertarVuelo();
				break;
			case 5:
				System.out.println("Borrar un vuelo");
				borrarVuelo();
				break;
			case 6:
				System.out.println("Modificar los vuelos de fumadores a no fumadores.");
				break;
			}
		
		
		
	}
	
	// M�todo 1
	public static void destinoMadrid() {
		String consulta = "SELECT * FROM vuelos WHERE DESTINO='MADRID'";
		
		try {
			stm = conexion.createStatement();
			rs = stm.executeQuery(consulta);
			while (rs.next()) {
				System.out.println("Vuelo de " + rs.getString(4) + " a Madrid:");
				System.out.println("------------------------------------------");
				System.out.println("C�digo de vuelo: " + rs.getString(1) + "\nHora de salida: "
						+ rs.getString(2) + "\nDestino: " + rs.getString(3)
						+ "\nProcedencia: " + rs.getString(4) + "\nPlazas de fumador: " + rs.getInt(5)
						+ "\nPlazas no fumador: " + rs.getInt(6) + "\nPlazas turista: "
						+ rs.getInt(7) + "\nPlazas primera clase: " + rs.getInt(8) + "\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stm.close();
				rs.close();
				conexion.close();
				System.out.println("Conexi�n con BD finalizada");
			} catch (Exception ex) {
				System.out.println("Error al cerrar conexi�n");
			}
		}
	}
	
	
	//M�todo 2
	public static void fumadoresVuelo() {
		String consulta = "SELECT PLAZAS_FUMADOR, PROCEDENCIA, DESTINO FROM `vuelos` WHERE PROCEDENCIA='Madrid' and DESTINO='Roma';";
		
		try {
			stm = conexion.createStatement();
			rs = stm.executeQuery(consulta);
			while (rs.next()) {
				System.out.println("Vuelo de " + rs.getString(2) + " a " + rs.getString(3));
				System.out.println("------------------------------------------");
				System.out.println("N�mero de plazas de fumador del vuelo: " + rs.getInt(1) + "\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stm.close();
				rs.close();
				conexion.close();
				System.out.println("Conexi�n con BD finalizada");
			} catch (Exception ex) {
				System.out.println("Error al cerrar conexi�n");
			}
		}
	}
	
	
	//M�todo 3
	public static void infoPasajeros() {
		System.out.println("Introduce el n�mero de vuelo a buscar: ");
		String eleccion = entrada.next();
		String consulta= "SELECT * FROM `pasajeros` WHERE COD_VUELO='" + eleccion + "';";
		
		try {
			stm = conexion.createStatement();
			rs = stm.executeQuery(consulta);
			while (rs.next()) {
				System.out.println("\nVuelo " + rs.getString(2));
				System.out.println("------------------------------------------");
				System.out.println("N�mero de pasajero: " + rs.getInt(1) + "\n"
						+ "C�digo del vuelo: " + rs.getString(2) + "\n"
						+ "Tipo de plaza: " + rs.getString(3) +"\n"
						+ "Fumador: " + rs.getString(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stm.close();
				rs.close();
				conexion.close();
				System.out.println("Conexi�n con BD finalizada");
			} catch (Exception ex) {
				System.out.println("Error al cerrar conexi�n");
			}
		}
	}

	// M�todo 4
	public static void insertarVuelo() {
		System.out.println("Insertando nuevo vuelo");
		System.out.println("----------------------");
		System.out.println("Introduce el n�mero de vuelo a insertar: ");
		String numeroVuelo = entrada.next();
		System.out.println("Introduce fecha de vuelo. FORMATO: DD/MM/AA-HH:MM");
		String fecha = entrada.next();
		System.out.println("Introduce el origen del vuelo: ");
		String origen = entrada.next();
		System.out.println("Introduce el destino del vuelo:");
		String destino = entrada.next();
		System.out.println("Introduce el n�mero de plazas de fumador del vuelo:");
		int fumador = entrada.nextInt();
		System.out.println("Introduce el n�mero de plazas de no fumador del vuelo:");
		int noFumador = entrada.nextInt();
		System.out.println("Introduce el n�mero de plazas en clase turista:");
		int turista = entrada.nextInt();
		System.out.println("Introduce el n�mero de plazas en primera clase:");
		int bussiness = entrada.nextInt();
		
		String consulta= "INSERT INTO `vuelos` VALUES ('"+numeroVuelo.toUpperCase()+"', "+"'"+fecha+"', " + "'" + origen.toUpperCase() + "', " + "'" + destino.toUpperCase() + "', "
														+ fumador + "," + noFumador + "," + turista + ","  + bussiness +");";
		
		try {
			stm = conexion.createStatement();
			stm.executeUpdate(consulta);
			System.out.println("Vuelo " + numeroVuelo + " introducido conrrectamente");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stm.close();
				conexion.close();
				System.out.println("Conexi�n con BD finalizada");
			} catch (Exception ex) {
				System.out.println("Error al cerrar conexi�n");
			}
		}
	}
	
	
	//M�todo 5
	public static void borrarVuelo() {
		System.out.println("Introduce el n�mero de vuelo a borrar: ");
		String eleccion = entrada.next();
		String consulta= "DELETE FROM `vuelos` WHERE COD_VUELO='" + eleccion.toUpperCase() + "';";
		
		try {
			stm = conexion.createStatement();
			stm.executeUpdate(consulta);
			System.out.println("Vuelo " + eleccion.toUpperCase() + " borrado correctamente de la BD");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stm.close();
				conexion.close();
				System.out.println("Conexi�n con BD finalizada");
			} catch (Exception ex) {
				System.out.println("Error al cerrar conexi�n");
			}
		}
	}
	
	
	//M�todo 6
	public static void fumNofum() {
		System.out.println("Cambiar fumadores a No fumadores.");
		System.out.println("---------------------------------");
		String eleccion = entrada.next();
		String consulta= "SELECT * FROM `pasajeros` WHERE COD_VUELO='" + eleccion + "';";
		
		try {
			stm = conexion.createStatement();
			rs = stm.executeQuery(consulta);
			while (rs.next()) {
				System.out.println("\nVuelo " + rs.getString(2));
				System.out.println("------------------------------------------");
				System.out.println("N�mero de pasajero: " + rs.getInt(1) + "\n"
						+ "C�digo del vuelo: " + rs.getString(2) + "\n"
						+ "Tipo de plaza: " + rs.getString(3) +"\n"
						+ "Fumador: " + rs.getString(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stm.close();
				rs.close();
				conexion.close();
				System.out.println("Conexi�n con BD finalizada");
			} catch (Exception ex) {
				System.out.println("Error al cerrar conexi�n");
			}
		}
	}

}
