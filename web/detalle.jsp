<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*,cliente.Cliente"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <%
      //      out.println(request.getParameter("ver"));
        EntityManagerFactory emf =
          (EntityManagerFactory)getServletContext().getAttribute("emf");
       EntityManager em = emf.createEntityManager();
        
            Cliente c=em.find(Cliente.class,request.getParameter("ver"));
            out.println("NIF: "+c.getNif());
            out.println("Nombre: "+c.getNombre());
            out.println("Telefono: "+c.getTelefono());            
        %>
        
    </body>
</html>
