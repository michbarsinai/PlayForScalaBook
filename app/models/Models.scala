package models

case class Product(
  id: Long,
  ean: Long,
  name: String,
  description: String )

case class Warehouse(
  id: Long, 
  name: String )

case class StockItem(
  id: Long, 
  productId: Long, 
  warehouseId: Long, 
  quantity: Long )

object Product {
  import anorm._
  import play.api.Play.current
  import play.api.db.DB
  import anorm.RowParser
  import anorm.ResultSetParser
  import anorm.~
  import anorm.SqlParser._

  val selectAllQry = SQL("select * from products order by name asc")
  val findByEan = SQL("select * from products where ean={ean}")
  val prodsAndItems =  SQL("""
    SELECT p.*, s.*
    FROM products p
     INNER JOIN stock_items s ON p.id = s.product_id 
    """)
  val prodsForItem =  SQL("""
    SELECT *
    FROM stock_items
    WHERE product_id={product_id}
    """)


  val productParser: RowParser[Product] = {
    long("products.id")~long("products.ean")~str("products.name")~str("products.description") map {
      case id~ean~name~description => Product(id, ean, name, description)
    }
  }
  
  val productsParser: ResultSetParser[List[Product]] = { 
    productParser *
  }

  val stockItemParser: RowParser[StockItem] = {
    long("stock_items.id")~long("stock_items.product_id")~long("stock_items.warehouse_id")~long("stock_items.quantity") map {
      case id~prodId~wareHouseId~quantity => StockItem(id,prodId,wareHouseId,quantity)
    }
  }

  val stockItemsParser: ResultSetParser[List[StockItem]] = {
    stockItemParser*
  }
  
  val productStockItemParser: RowParser[(Product, StockItem)] = {
    productParser ~ stockItemParser map (flatten)
  }

  def find( ean:Long ):Option[Product] = DB.withConnection { implicit c =>
    findByEan.on(
      "ean" -> ean).as(
        productsParser).headOption
      // Note: we could also cache this.
  }
  
  def selectAllParser:List[Product] = DB.withConnection {
    implicit c => selectAllQry.as(productsParser)
  }

  def selectAllStream:List[Product] = DB.withConnection {
    implicit c =>
    selectAllQry().map( row => 
      Product( row[Long]("id"), row[Long]("ean"), row[String]("name"), row[String]("description"))
      ).toList
  }

  def selectAllProductsAndItems: Map[Product,Seq[StockItem]] = DB.withConnection{ implicit c =>
    val res:List[(Product, StockItem)] = prodsAndItems.as( productStockItemParser * )
    res.groupBy( _._1 ).mapValues( _.map(_._2) )
  }

  def listStockItemsFor( id:Long ) = DB.withConnection { implicit c =>
    prodsForItem.on( "product_id" -> id ).as( stockItemsParser )
  }

  def selPSI1 = DB.withConnection{ implicit c =>
    prodsAndItems.as( productParser~stockItemParser map (flatten) * )
  }

  def selPSI2 = selPSI1.groupBy( _._1 )
  def selPSI3 = selPSI2.mapValues( _.map( _._2) )
  
}
