package br.org.serratec.backend.service;

//import java.io.IOException;
//import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.org.serratec.backend.config.MailConfig;
import br.org.serratec.backend.dto.AlterarClienteDTO;
import br.org.serratec.backend.dto.ClienteDTO;
import br.org.serratec.backend.dto.EnderecoDTO;
import br.org.serratec.backend.dto.InserirClienteDTO;
import br.org.serratec.backend.dto.InserirEnderecoDTO;
import br.org.serratec.backend.exception.RecursoBadRequestException;
import br.org.serratec.backend.exception.RecursoNotFoundException;
import br.org.serratec.backend.model.Cliente;
import br.org.serratec.backend.model.Endereco;
import br.org.serratec.backend.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	EnderecoService enderecoService;

	@Autowired
	FotoClienteService fotoClienteService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	MailConfig mailConfig;

	/**
	 * METODO PARA INSERIR UM CLIENTE
	 * 
	 * @param cliente
	 * @return UM NOVO CLIENTE
	 * @throws RecursoBadRequestException
	 */

	public ClienteDTO inserir(InserirClienteDTO inserirClienteDTO) throws RecursoBadRequestException {

		if (clienteRepository.findByEmail(inserirClienteDTO.getEmail()) != null) {
			throw new RecursoBadRequestException("Email já cadastrado!");
		}
		if (clienteRepository.findByCpf(inserirClienteDTO.getCpf()) != null) {
			throw new RecursoBadRequestException("CPF ja cadastrado!");
		}
		if (clienteRepository.findByNomeUsuario(inserirClienteDTO.getNomeUsuario()) != null) {
			throw new RecursoBadRequestException("Nome de Usuário ja cadastrado!");
		}

		Cliente cliente = new Cliente();
		cliente.setCpf(inserirClienteDTO.getCpf());
		cliente.setDataNascimento(inserirClienteDTO.getDataNascimento());
		cliente.setNomeCompleto(inserirClienteDTO.getNomeCompleto());
		cliente.setTelefone(inserirClienteDTO.getTelefone());
		cliente.setNomeUsuario(inserirClienteDTO.getNomeUsuario());
		cliente.setEmail(inserirClienteDTO.getEmail());
		cliente.setNumero(inserirClienteDTO.getNumero());
		cliente.setComplemento(inserirClienteDTO.getComplemento());
		InserirEnderecoDTO iedto = new InserirEnderecoDTO(inserirClienteDTO.getEndereco());
		EnderecoDTO enderecoDTO = enderecoService.inserir(iedto);
		Endereco endereco = new Endereco(enderecoDTO.getCep(), enderecoDTO.getLogradouro(), 
		enderecoDTO.getBairro(), enderecoDTO.getLocalidade(), enderecoDTO.getUf());
		cliente.setEndereco(endereco);
		cliente.setSenha(bCryptPasswordEncoder.encode(inserirClienteDTO.getSenha()));
		//mailConfig.enviarEmail(cliente.getEmail(), "Cadastro de Usuário Concluído", cliente.toString());
		clienteRepository.save(cliente);
		return new ClienteDTO(cliente);

	}

	/**
	 * METODO PARA LISTAR OS CLIENTES
	 * 
	 * @return LISTA DE CLIENTES
	 */
	public List<ClienteDTO> listar() {
		List<Cliente> clientes = clienteRepository.findAll();
		List<ClienteDTO> clientesDTO = new ArrayList<ClienteDTO>();

		for (Cliente cliente : clientes) {
			ClienteDTO clienteDTO = new ClienteDTO(cliente);
			clientesDTO.add(clienteDTO);
		}

		return clientesDTO;
	}

	/**
	 * METODO PARA EDITAR UM REGISTRO DE CLIENTE
	 * 
	 * @param alterarClienteDTO
	 * @return UM CLIENTE COM REGISTRO ALTERADO
	 * @throws EmailException
	 */
	public ClienteDTO alterar(AlterarClienteDTO alterarClienteDTO) throws RecursoBadRequestException {

		if (clienteRepository.findByEmail(alterarClienteDTO.getEmail()) != null) {
			throw new RecursoBadRequestException("Email já cadastrado!");
		}
		if (clienteRepository.findByCpf(alterarClienteDTO.getCpf()) != null) {
			throw new RecursoBadRequestException("CPF ja cadastrado!");
		}
		if (clienteRepository.findByNomeUsuario(alterarClienteDTO.getNomeUsuario()) != null) {
			throw new RecursoBadRequestException("Nome de Usuário ja cadastrado!");
		}
		Cliente cliente = new Cliente();
		cliente.setId(alterarClienteDTO.getId());

		cliente.setSenha(bCryptPasswordEncoder.encode(alterarClienteDTO.getSenha()));
		clienteRepository.save(cliente);
		mailConfig.enviarEmail(cliente.getEmail(), "Cadastro de Usuário Alterado", cliente.toString());
		return new ClienteDTO(cliente);
	}

	/**
	 * METODO PARA LISTAR CLIENTE POR NUMERO COM TOTAL GERAL
	 * 
	 * @param id
	 * @return UM CLIENTE
	 */
	public ClienteDTO buscar(Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if (cliente.isPresent()) {
			return new ClienteDTO(cliente.get());
		} else {
			throw new RecursoNotFoundException("Cliente não encontrado");
		}
	}

	/*public ClienteDTO inserirFoto(Cliente cliente, MultipartFile file) throws IOException {
		fotoClienteService.inserir(clienteRepository.save(cliente), file);
		return adicionarUriFoto(cliente);
	}

	private ClienteDTO adicionarUriFoto(Cliente cliente) {
		URI uri = ServletUriComponentsBuilder
				  .fromCurrentContextPath()
				  .path("/funcionarios/{id}/foto")
				  .buildAndExpand(cliente.getId())
				  .toUri();
		
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setNomeUsuario(cliente.getNomeUsuario());
		clienteDTO.setUri(uri.toString());
		
		return clienteDTO;
	}
*/
	/**
	 * METODO PARA DELETAR UM CLIENTE
	 * 
	 * @param id
	 */
	public void deletar(Long id) {
		if (clienteRepository.existsById(id)) {
			clienteRepository.deleteById(id);
		}
	}

}
