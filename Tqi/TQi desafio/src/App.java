public class Categoria {
    public static void main(String[] args) throws Exception {
        @Entity
data class Categoria(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val nome: String
)

@Entity
data class Produto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val nome: String,
    val preco: Double,
    @ManyToOne
    val categoria: Categoria
)

@Repository
interface CategoriaRepository : JpaRepository<Categoria, Long>

@Repository
interface ProdutoRepository : JpaRepository<Produto, Long>


@RestController
@RequestMapping("/categorias")
class CategoriaController(private val categoriaRepository: CategoriaRepository) {

    @GetMapping
    fun listarCategorias(): List<Categoria> {
        return categoriaRepository.findAll()
    }

    @PostMapping
    fun criarCategoria(@RequestBody categoria: Categoria): Categoria {
        return categoriaRepository.save(categoria)
    }
}

@RestController
@RequestMapping("/produtos")
class ProdutoController(private val produtoRepository: ProdutoRepository) {

    @GetMapping
    fun listarProdutos(): List<Produto> {
        return produtoRepository.findAll()
    }

    @PostMapping
    fun criarProduto(@RequestBody produto: Produto): Produto {
        return produtoRepository.save(produto)
    }
}

@RestController
@RequestMapping("/carrinho")
class CarrinhoController {
    private val carrinho = mutableListOf<Produto>()

    @PostMapping("/{produtoId}")
    fun adicionarProduto(@PathVariable produtoId: Long) {
        val produto = // Obtenha o produto pelo ID usando o produtoRepository.findById(produtoId)
        carrinho.add(produto)
    }

    @GetMapping
    fun listarCarrinho(): List<Produto> {
        return carrinho
    }
}

@RestController
@RequestMapping("/vendas")
class VendasController(private val produtoRepository: ProdutoRepository) {

    @GetMapping("/finalizar")
    fun finalizarVenda(): Double {
        val total = carrinho.sumByDouble { it.preco }
        carrinho.clear()
        return total
    }
}


    }
}
