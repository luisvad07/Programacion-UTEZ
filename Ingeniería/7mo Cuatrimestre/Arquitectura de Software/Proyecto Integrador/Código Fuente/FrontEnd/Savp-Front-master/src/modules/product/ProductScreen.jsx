import React, { useContext, useEffect, useState } from "react";
import { AuthContext } from "../auth/authContext";
import AxiosClient from "../../shared/plugins/axios";
import DataTable from "react-data-table-component";
import FeatherIcon from "feather-icons-react/build/FeatherIcon";
import { Button } from "react-bootstrap";
import ProductForm from "./components/ProductForm";
import ProductEditForm from "./components/ProductEditForm"; // Importa ProductEditForm
import Swal from 'sweetalert2';
import { useTheme } from "../../shared/components/ThemeContext";
import '../../utils/styles/AdminStyle.css'


const ProductScreen = () => {
  const user = useContext(AuthContext);
  const { token } = user;
  const [products, setProducts] = useState([]);
  const [showModalForm, setShowModalForm] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [editProductData, setEditProductData] = useState(null);
  const { darkMode } = useTheme();


  const getAllProducts = async () => {
    try {
      const response = await AxiosClient({
        url: "/producto/",
        method: "GET",
        headers: { Authorization: `Bearer ${token}` },
      });
      setProducts(response);
      console.log(products);
    } catch (error) {
      console.log(error);
    }
  };

  const eliminate = async (id) => {
    try {
      const response = await AxiosClient({
        url: `/producto/${id}`,
        method: "DELETE",
        headers: { Authorization: `Bearer ${token}` },
      });
      Swal.fire({
        icon: 'success',
        title: 'Eliminado',
        text: 'El producto ha sido eliminado exitosamente',
      });
    } catch (error) {
      console.log(error);
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'Ha ocurrido un error al eliminar el producto',
      });
    } finally {
      getAllProducts();
    }
  };

  const handleEdit = (productData) => {
    setEditProductData(productData);
    setIsEditing(true);
    setShowModalForm(true);
  };

  useEffect(() => {
    document.title = "Productos";

    getAllProducts();
  }, []);

  const columns = React.useMemo(
    () => [
      {
        name: "ID",
        selector: (row) => row.id,
      },
      {
        name: "Titulo",
        selector: (row) => row.titulo,
        sortable: true,
        fixed: true,
      },
      {
        name: "Descripcion",
        selector: (row) => row.descripcion,
        sortable: true,
        fixed: true,
      },
      {
        name: "Imagen",
        cell: (row) => (
          // En tu componente React
          <img
            src={row.imageUrl}
            alt={`Imagen de ${row.titulo}`}
            style={{ width: "50px" }}
          />
        ),
      },
      {
        name: "ACCIONES",
        cell: (row) => (
          <>
            <Button
              variant="warning"
              type="btn btn-outline-warning btn-circle me-1"
              size={16}
              onClick={() => handleEdit(row)}
            >
              <FeatherIcon icon={"edit"} />
            </Button>
            <Button
              variant="danger"
              size={15}
              onClick={() => eliminate(row.id)}
            >
              <FeatherIcon icon={"trash"} />
            </Button>
          </>
        ),
      },
    ],
    [eliminate]
  );

  return (
    <>
      <div
        className={`CrudContainer ${darkMode ? 'dark-mode' : 'light-mode'}`}
        style={{
          justifyContent: "center",
          alignItems: "center",
          height: "92vh",
          padding: 20,
          overflowY: "auto"
        }}
      >
        <div>
          <div className="App">
            <DataTable
              title={
                <div style={{ display: "flex", flexDirection: "row" }}>
                  <div style={{ width: "95%", paddingTop: 3 }}>Productos</div>
                  <div>
                    <FeatherIcon
                      className="DataIcon"
                      icon={"plus"}
                      onClick={() => {
                        setIsEditing(false);
                        setShowModalForm(true);
                      }}
                      style={{ height: 40, width: 40 }}
                    />
                  </div>
                </div>
              }
              columns={columns}
              data={products}
              pagination
              highlightOnHover
              paginationPerPage={8}
              paginationComponentOptions={{
                rowsPerPageText: "",
                noRowsPerPage: true,
              }}
            />
          </div>
        </div>
      </div>
      {isEditing ? (
        <ProductEditForm
          isOpen={showModalForm}
          productData={editProductData}
          token={token}
          onClose={() => {
            setIsEditing(false);
            setShowModalForm(false);
            setEditProductData(null);
            getAllProducts(); // Actualiza la lista después de la edición
          }}
        />
      ) : (
        <ProductForm
          isOpen={showModalForm}
          data={getAllProducts}
          token={token}
          onClose={() => setShowModalForm(false)}
        />
      )}
    </>
  );
};

export default ProductScreen;
