package br.org.serratec.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.backend.dto.InserirPedidoItemDTO;
import br.org.serratec.backend.dto.PedidoItemDTO;
import br.org.serratec.backend.exception.RecursoBadRequestException;
import br.org.serratec.backend.exception.RecursoNotFoundException;
import br.org.serratec.backend.model.PedidoItem;
import br.org.serratec.backend.repository.PedidoItemRepository;

@Service
public class PedidoItemService {

	@Autowired
	PedidoItemRepository pedidoItemRepository;

	/**
	 * METODO PARA INSERIR UM PRODUTO
	 * 
	 * @param produto
	 * @return UM NOVO PRODUTO
	 */
	public PedidoItemDTO inserir(InserirPedidoItemDTO inserirPedidoItemDTO) {

		if (pedidoItemRepository.findBypedido(inserirPedidoItemDTO.getId_pedido()) != null) {
			throw new RecursoBadRequestException("Produto ja inserido!");
		}

		PedidoItem pedidoItem = new PedidoItem();
		pedidoItem.setPedido(inserirPedidoItemDTO.getId_pedido());
		pedidoItem.setProduto(inserirPedidoItemDTO.getId_produto());
		pedidoItem.setQntProduto(inserirPedidoItemDTO.getQntProduto());
		pedidoItem.setSubTotal(inserirPedidoItemDTO.getSubTotal());
		pedidoItem.setVlrUnit(inserirPedidoItemDTO.getVlrUnit());
		pedidoItemRepository.save(pedidoItem);

		return new PedidoItemDTO(pedidoItem);
	}

	/**
	 * METODO PARA LISTAR PEDIDO ITEM
	 * 
	 * @return UMA LISTA DE PEDIDO ITEM
	 */
	public List<PedidoItemDTO> listar() {
		List<PedidoItem> pedidoItems = pedidoItemRepository.findAll();
		return pedidoItems.stream().map(pedidoItem -> new PedidoItemDTO(pedidoItem)).collect(Collectors.toList());
	}

	/**
	 * METODO PARA BUSCAR PEDIDO ITEM POR ID
	 * 
	 * @param id
	 * @return UM PEDIDO ITEM POR ID
	 */
	public PedidoItemDTO buscar(Long id) {
		Optional<PedidoItem> pedidoItem = pedidoItemRepository.findById(id);
		if (pedidoItem.isPresent()) {
			return new PedidoItemDTO(pedidoItem.get());
		} else {
			throw new RecursoNotFoundException("Produto não encontrado");
		}
	}

	/**
	 * METODO PARA DELETAR PEDIDO ITEM
	 * 
	 * @param id
	 */
	public void deletar(Long id) {
		if (pedidoItemRepository.existsById(id)) {
			pedidoItemRepository.deleteById(id);
		}
	}

}
