package dao

/**
 * Created by Engin Yoeyen on 30/09/14.
 */
object QueryBuilder {

  val tables = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'; "

  def select(name: String)  =  s"SELECT * FROM $name ;"

  def columns(name: String)  =  s"SELECT column_name, data_type FROM information_schema.columns WHERE table_schema = 'public' AND table_name = '$name' ;"

  def selectWhere(name: String, column:String, filter:String)  =  s"SELECT * FROM $name WHERE $column = '$filter' ;"
}
