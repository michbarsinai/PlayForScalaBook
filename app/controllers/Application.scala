package controllers

import play.api._
import play.api.mvc._

import models._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready.", Product.selectAllProductsAndItems) )
    // Ok(views.html.index("Your new application is ready.", Product.selectAllStream) )
  }
  def dbgProductList = Action { implicit request =>
    Ok( Product.selPSI1.map( _.toString ).mkString("\n\n") )
  }
}