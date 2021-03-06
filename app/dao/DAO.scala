package dao

import models._
import anorm._

import scala.concurrent.Future

import play.api.http.Status.BAD_REQUEST
import java.sql.Timestamp
import org.joda.time.DateTime

// IMPORTANT import this to have the required tools in your scope
import play.api.libs.json._
// imports required functional generic structures
import play.api.libs.functional.syntax._
import play.api.libs.json.Writes._

/**
 * Created by Engin Yoeyen on 30/09/14.
 */
object DAO {

  def getTables() = Future {
    DB.withConnection { implicit connection =>
      SQL(QueryBuilder.tables).as(models.Table.table *)
    }
  }




  def execute(statement:String) = Future {
    Logger.debug(statement)
    val list = DB.withConnection { implicit connection =>
      val values  = SQL(statement)

      try{
        val jsonList = values().map ( row => {
          val rowData = new models.Row()
          for (columnList <- row.metaData.ms) {
            rowData.update(columnList.column.alias.get)(extractValue(columnList.clazz,row,columnList.column.alias.get))
          }
          rowData
        }
        ).toList

        jsonList
      }catch{
        case e:Exception => {
          Logger.error(e.getMessage)
          models.Error(e.getMessage)
        }
      }

    }
    list
  }

  private def extractValue(clazz:String, row:SqlRow, value:String): Any = {

    clazz match {
      case "java.lang.Integer" => row[Int](value)
      case "java.lang.String" => row[String](value)
      case "java.lang.Boolean" => row[Boolean](value)
      case "java.lang.Long" => row[Long](value)
      case "java.lang.Float" => row[Float](value)
      case "java.math.BigInteger" =>  row[java.math.BigInteger](value)
      case "java.math.BigDecimal" =>  row[java.math.BigDecimal](value)
      case "java.sql.Date" =>  row[Date](value)
      case "java.sql.Timestamp" =>  row[Timestamp](value)
      case "java.sql.Time" =>  row[java.sql.Time](value)
      case _ => throw new RuntimeException("Type not found : "+ clazz )
    }
  }


  implicit def rowToFloat: Column[Float] = Column.nonNull { (value, meta) =>
    val MetaDataItem(qualified, nullable, clazz) = meta
    value match {
      case int: Int => Right(int: Float)
      case long: Long => Right(long: Float)
      case float: Float => Right(float)
      case _ => Left(TypeDoesNotMatch("Cannot convert " + value + ":" + value.asInstanceOf[AnyRef].getClass + " to Float for column " + qualified))
    }
  }

  implicit def rowToTimestamp: Column[Timestamp] = Column.nonNull { (value, meta) =>
    val MetaDataItem(qualified, nullable, clazz) = meta
    value match {
      case ts: java.sql.Timestamp => Right(ts)
      case _ => Left(TypeDoesNotMatch("Cannot convert " + value + ":" + value.asInstanceOf[AnyRef].getClass) )
    }
  }

  implicit def rowToTime: Column[java.sql.Time] = Column.nonNull { (value, meta) =>
    val MetaDataItem(qualified, nullable, clazz) = meta
    value match {
      case time:  java.sql.Time => Right(time)
      case _ => Left(TypeDoesNotMatch("Cannot convert " + value + ":" + value.asInstanceOf[AnyRef].getClass) )
    }
  }
}
