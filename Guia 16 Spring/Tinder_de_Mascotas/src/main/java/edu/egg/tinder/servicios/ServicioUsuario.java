/*
 * Sin licencia.
 * Uso para capacitación
 * 2021 Año de la Prevención y Lucha contra el COVID-19.
 */
package edu.egg.tinder.servicios;

import edu.egg.tinder.entidades.Foto;
import edu.egg.tinder.entidades.Usuario;
import edu.egg.tinder.errores.ErrorServicio;
import edu.egg.tinder.repositorios.RepoUsuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Adrian E. Camus
 */
@Service
public class ServicioUsuario implements UserDetailsService {

    // creo un objeto de la clase RepoUsuario y lo denomino rUsuario
    @Autowired
    RepoUsuario rUsuario;

    @Autowired
    ServicioFoto sFoto;
    
    @Autowired
    ServicioNotificacion sNotificacion;
    
    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String apellido, String mail, String clave) throws ErrorServicio {
        //creo un un objeto de tipo Usuario y lo denomino "usuario"
        Usuario usuario = new Usuario();
        //con los parametros recibidos invoco al metodo validar
        validar(nombre, apellido, mail, clave);
        //en esta linea si el metodo validar no arroja excepcion continuo seteando los parametros al usuario
        usuario.setAlta(new Date());
        usuario.setApellido(apellido);
        usuario.setNombre(nombre);
        usuario.setMail(mail);
        //mediante la clase BCryptPasswordEncoder encriptamos la clave y la persistimos en la base de datos ya encriptada
        String encriptada = new BCryptPasswordEncoder().encode(clave);        
        usuario.setClave(encriptada);
        
        //mediante el servicio de foto con su metodo guardar creo un objeto de tipo foto
        Foto foto = sFoto.guardar(archivo);
        //seteo la foto a mi usuario
        usuario.setFoto(foto);

        //Mediante el Repositorio de usuario utilizo el metodo save para persistir en la base
        rUsuario.save(usuario);
        
        //sNotificacion.enviar("Bienvenido al Tinder de Mascotas", "Tinder de Mascotas", usuario.getMail());
    }
    
    @Transactional
    public void modificar(MultipartFile archivo, String id, String apellido, String nombre, String mail, String clave) throws ErrorServicio {

        validar(nombre, apellido, mail, clave);

        Optional<Usuario> respuesta = rUsuario.findById(id);

        if (respuesta.isPresent())
        {

            Usuario usuario = respuesta.get();

            usuario.setApellido(apellido);
            usuario.setMail(mail);
            usuario.setNombre(nombre);
            
            String encriptada = new BCryptPasswordEncoder().encode(clave);        
            usuario.setClave(encriptada);
            
            /*Creo una varible String llamado idFoto como null, luego
            pregunto si el usuario tiene una foto usando el metodo getFoto 
            y verificando que sea DISTINTO de nulo, en ese caso a mi variable 
            idFoto que habia creado como nula lo igualo al id de la foto del 
            usuario. luego mediante el metodo modificar del servicio de foto
            creo un objeto de tipo foto, para poderselo setear al usuario*/
            String idFoto = null;
            if (usuario.getFoto() != null)
            {
                idFoto = usuario.getFoto().getId();
            }
            Foto foto = sFoto.modificar(idFoto, archivo);

            usuario.setFoto(foto);

            rUsuario.save(usuario);
        } else
        {
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }

    }
    
    @Transactional
    public void deshabilitar(String id) throws ErrorServicio {
        Optional<Usuario> respuesta = rUsuario.findById(id);

        if (respuesta.isPresent())
        {

            Usuario usuario = respuesta.get();

            usuario.setBaja(new Date());

            rUsuario.save(usuario);
        } else
        {
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }
    }
    
    @Transactional
    public void habilitar(String id) throws ErrorServicio {
        Optional<Usuario> respuesta = rUsuario.findById(id);

        if (respuesta.isPresent())
        {

            Usuario usuario = respuesta.get();

            usuario.setBaja(null);

            rUsuario.save(usuario);
        } else
        {
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }
    }

    public void validar(String nombre, String apellido, String mail, String clave) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty())
        {
            throw new ErrorServicio("El Nombre no puede ser nulo");
        }

        if (apellido == null || apellido.isEmpty())
        {
            throw new ErrorServicio("El Apellido no puede ser nulo");
        }

        if (mail == null || mail.isEmpty())
        {
            throw new ErrorServicio("El mail no puede ser nulo");
        }

        if (clave == null || clave.isEmpty() || clave.length() <= 5)
        {
            throw new ErrorServicio("La clave no puede ser menor a 6 caracteres");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = rUsuario.buscarPorMail(mail);

        if (usuario != null)
        {
            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("MODULO_FOTOS");
            permisos.add(p1);
            GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO_MASCOTAS");
            permisos.add(p2);
            GrantedAuthority p3 = new SimpleGrantedAuthority("MODULO_VOTOS");
            permisos.add(p3);

            User user = new User(usuario.getMail(), usuario.getClave(), permisos);
            return user;
        } else
        {
            return null;
        }

    }

}
