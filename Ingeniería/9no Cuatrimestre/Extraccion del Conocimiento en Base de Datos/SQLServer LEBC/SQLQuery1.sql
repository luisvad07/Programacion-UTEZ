select * from Sales.SalesOrderHeader;

SELECT C.ProductCategoryID, C.Name
FROM Production.ProductCategory C
ORDER BY C.ProductCategoryID ASC;

SELECT T.TerritoryID, T.Name
FROM Sales.SalesTerritory T
ORDER BY T.TerritoryID ASC

SELECT P.ProductID, P.Name
FROM Production.Product P
ORDER BY P.ProductID ASC

SELECT HE.BusinessEntityID, he.JobTitle, PP.FirstName, PP.LastName
FROM Sales.SalesPerson E
INNER JOIN HumanResources.Employee HE ON HE.BusinessEntityID =
E.BusinessEntityID
INNER JOIN Person.Person PP ON PP.BusinessEntityID = HE.BusinessEntityID
ORDER BY HE.BusinessEntityID ASC

SELECT DISTINCT MONTH(S.OrderDate) AS MES, YEAR(S.OrderDate) AS ANIO
FROM Sales.SalesOrderHeader S
ORDER BY 1 ASC, 2 ASC

SELECT SP.BusinessEntityID, P.ProductID, PC.ProductCategoryID, T.TerritoryID,
MONTH(S.OrderDate) AS MES, YEAR(S.OrderDate) AS ANIO,
SUM(D.UnitPrice * D.OrderQty) AS MONTO
FROM Sales.SalesOrderHeader S
INNER JOIN Sales.SalesOrderDetail D ON S.SalesOrderID = D.SalesOrderID
INNER JOIN Production.Product P ON P.ProductID = D.ProductID
INNER JOIN Sales.SalesTerritory T ON T.TerritoryID = S.TerritoryID
INNER JOIN Sales.SalesPerson SP ON SP.BusinessEntityID = S.SalesPersonID

INNER JOIN Production.ProductCategory PC ON PC.ProductCategoryID =
P.ProductSubcategoryID
GROUP BY SP.BusinessEntityID, P.ProductID, PC.ProductCategoryID, T.TerritoryID,
MONTH(S.OrderDate), YEAR(S.OrderDate)
ORDER BY YEAR(S.OrderDate) ASC, MONTH(S.OrderDate) ASC