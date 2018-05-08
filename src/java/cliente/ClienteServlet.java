package cliente;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author josem
 */
public class ClienteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    @Override
    protected void doGet(
        HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        // Se obtiene una instancia del Gestor de Persistencia 
        EntityManagerFactory emf =
           (EntityManagerFactory)getServletContext().getAttribute("emf");
        EntityManager em = emf.createEntityManager();
       
        try {
            // Procesa un cliente nuevo enviado por la pagina Web
            // recogemos los parametros de los campos input y asignamos su valor
            // a variables locales que seran procesadas
            String nif = request.getParameter("nif");
            String nombre= request.getParameter("nombre");
            String telefono = request.getParameter("telefono");
 
            String resultado=""; 
            
            if (nif !=null) {
                
                if(nif.isEmpty()){
                    resultado=" ** El Cliente debe tener NIF para agregarlo a la Base de Datos **";                    
                 }
                  else{
                     //buscamos en la base de datos un registro con la misma nif
                     // si existe se carga el objeto Cliente en repetido, sino
                     // repetido será null
                        em.getTransaction().begin();
                        Cliente repetido=em.find(Cliente.class,nif);
                        
                    // comporbamos que repetido sea null asi sabemos que el cliente
                    // es nuevo y entonces grabamos el Nuevo Objeto Cliente
                  if(repetido==null){
                        em.persist(new Cliente(nif,nombre,telefono));
                        em.getTransaction().commit();
                        resultado=" ** Registro grabado con correctamente **";
                  } 
                   else{
                        resultado=" ** Ya existe un Registro en la Base Datos con el NIF: "+nif;
                   }
                }
            }
 
            // Muestra una lista de los Clientes guardados en la Base de Datos
            // Incluyendo el nuevo si hubo alguno.
            List<Cliente> clienteList =
                em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
            
            request.setAttribute("mensaje",resultado);            
            request.setAttribute("clientes", clienteList);
            request.getRequestDispatcher("/cliente.jsp").forward(request, response);
 
        }
        finally {
            // Cierra el gestor de persistencia
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
                em.close();
            }
        }
    }
    
    @Override
    protected void doPost(
        HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
