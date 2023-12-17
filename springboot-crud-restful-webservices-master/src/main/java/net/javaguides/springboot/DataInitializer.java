package net.javaguides.springboot;

import net.javaguides.springboot.entity.*;
import net.javaguides.springboot.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EdicaoRepository edicaoRepository;

    @Autowired
    private EspacoRepository espacoRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        criarUsuarios();
        criarEventosEdicoes();
        criarEspacos();
        criarAtividades();
    }

    private void criarUsuarios() {
        // Criação do Administrador
        if (userRepository.findByLogin("admin").isEmpty()) {
            User admin = new User();
            admin.setLogin("admin");
            admin.setEmail("admin@example.com");
            admin.setNome("Administrador");
            admin.setSenha(passwordEncoder.encode("adminPassword"));
            admin.setRoles(Set.of("ROLE_ADMIN")); // Define o papel de administrador
            userRepository.save(admin);
        }

        // Criação do Organizador
        if (userRepository.findByLogin("organizador").isEmpty()) {
            User organizador = new User();
            organizador.setLogin("organizador");
            organizador.setEmail("organizador@example.com");
            organizador.setNome("Organizador Eventos");
            organizador.setSenha(passwordEncoder.encode("senhaOrganizador"));
            organizador.setRoles(Set.of("ROLE_ORGANIZADOR")); // Define o papel de organizador
            userRepository.save(organizador);
        }

        // Criação de um usuário comum
        if (userRepository.findByLogin("usuarioComum").isEmpty()) {
            User usuarioComum = new User();
            usuarioComum.setLogin("usuarioComum");
            usuarioComum.setEmail("usuarioComum@example.com");
            usuarioComum.setNome("Usuario Comum");
            usuarioComum.setSenha(passwordEncoder.encode("senhaUsuario"));
            usuarioComum.setRoles(Set.of("ROLE_USER")); // Define o papel de usuário comum
            userRepository.save(usuarioComum);
        }

    }


    private void criarEventosEdicoes() {
        Evento evento1 = new Evento("Evento 1", "EVT1", "Descrição do Evento 1", "/evento1");
        eventoRepository.save(evento1);

        Edicao edicao1Evento1 = new Edicao(1, 2023, new Date(), new Date(), "Cidade 1");
        edicao1Evento1.setEvento(evento1);
        evento1.getEdicoes().add(edicao1Evento1); // Vincula a edição ao evento
        edicaoRepository.save(edicao1Evento1);
    }

    private void criarEspacos() {
        Espaco espaco1 = new Espaco("Espaço 1", "Localização 1", 100);
        espacoRepository.save(espaco1);

        Espaco espaco2 = new Espaco("Espaço 2", "Localização 2", 200);
        espacoRepository.save(espaco2);

    }

    private void criarAtividades() {
        Atividade atividade1 = new Atividade("Atividade 1", Atividade.TipoAtividade.PALESTRA, "Descrição da Atividade 1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
        atividadeRepository.save(atividade1);

        Atividade atividade2 = new Atividade("Atividade 2", Atividade.TipoAtividade.WORKSHOP, "Descrição da Atividade 2", LocalDateTime.now(), LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
        atividadeRepository.save(atividade2);


    }
}
