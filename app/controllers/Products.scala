package controllers

import play.api._
import play.api.mvc._
import play.api.data.Forms._
import play.api.data._
import models._

object Products extends Controller {

def list = Action{
  Ok(views.html.productList( Product.selectAllParser) )
}

def showItem(ean:Long) = Action {
  Product.find(ean) match {
    case Some(p) => Ok(views.html.product(p))
    case None    => NotFound("No such product " + ean)
  }
}

def showItemAlias(alias:String) = TODO

}