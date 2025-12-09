import repository.DataRetriever;
import model.Category;
import model.Product;

public class Main {

    public static void main(String[] arguments) throws Exception {

        DataRetriever dataRetriever = new DataRetriever();

        System.out.println("Catégories disponibles");
        for (Category category : dataRetriever.getAllCategories()) {
            System.out.println(category);
        }

        System.out.println("\nProduits page 1 taille 2");
        for (Product product : dataRetriever.getProductList(1, 2)) {
            System.out.println(product);
        }

        System.out.println("\nProduits contenant Laptop");
        for (Product product :
                dataRetriever.getProductsByCriteria("Laptop", null, null, null)) {
            System.out.println(product);
        }

        System.out.println("\nProduits Informatique page 1 taille 1");
        for (Product product :
                dataRetriever.getProductsByCriteria(
                        null,
                        "Informatique",
                        null,
                        null,
                        1,
                        1
                )) {
            System.out.println(product);
        }
    }
}
