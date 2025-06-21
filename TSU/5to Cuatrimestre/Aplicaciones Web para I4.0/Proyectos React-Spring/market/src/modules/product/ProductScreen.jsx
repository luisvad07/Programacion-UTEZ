import React, { useEffect, useState } from 'react'
import { Card, Col, Row, Badge } from 'react-bootstrap'
import DataTable from 'react-data-table-component'
import AxiosClient from '../../shared/plugins/axios'
import { ButtonCircle } from '../../shared/components/ButtonCircle'
import { Loading } from '../../shared/components/Loading'
import { FilterComponent } from '../../shared/components/FilterComponent'

import Alert, {
    confirmMsj, confirmTitle, errorMsj, errorTitle, successMsj, successTitle
} from '../../shared/plugins/alerts'
import { ProductForm } from './components/ProductForm'

const options = {
    rowsPerPageText: 'Registros por página',
    rangeSeparatorText: 'de'
}

export const ProductScreen = () => {
    const [products, setProducts] = useState([])
    const [selectedProduct, setSelectedProduct] = useState({})
    const [isLoading, setIsLoading] = useState(false)
    const [isEditing, setIsEditing] = useState(false)
    const [filterText, setFilterText] = useState("")
    const [isOpen, setIsOpen] = useState(false)

    const filteredProducts = products.filter(
        product => product.name && product.name.toLowerCase().includes(filterText.toLowerCase())
    )

    const getProducts = async () => {
        try {
            setIsLoading(true)
            const data = await AxiosClient({ url: '/product/' })
            console.log('Data', data.data);
            if (!data.error) setProducts(data.data)
        } catch (error) {
            //poner alerta de error
        } finally {
            setIsLoading(false)
        }
    }
    //Se ejecuta después del return
    useEffect(() => {
        getProducts()
    }, [])
    /*Recibe una dependencia, si está vacío solo se renderiza una vez, si no, se ejecuta cada que haya un cambio en la dependencia*/

    const enableOrDisable = (row) => {
        console.log('Row', row);
        Alert.fire({
            title: confirmTitle,
            text: confirmMsj,
            icon: 'warning',
            confirmButtonColor: '#009574',
            confirmButtonText: 'Aceptar',
            cancelButtonColor: '#DD6B55',
            cancelButtonText: 'Cancelar',
            reverseButtons: true,
            backdrop: true,
            showCancelButton: true,
            showLoaderOnConfirm: true,
            allowOutsideClick: () => !Alert.isLoading,
            preConfirm: async () => {
                row.status = !row.status
                console.log('Row', row);
                try {
                    const response = await AxiosClient({
                        method: 'PATCH',
                        url: '/product/',
                        data: JSON.stringify(row),
                    })
                    if (!response.error) {
                        Alert.fire({
                            title: successTitle,
                            text: successMsj,
                            icon: 'success',
                            confirmButtonColor: '#3085d6',
                            confirmButtonText: 'Aceptar'
                        })
                    }
                    console.log('response', response);
                    return response
                } catch (error) {
                    Alert.fire({
                        title: errorTitle,
                        text: errorMsj,
                        icon: 'error',
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: 'Aceptar'
                    })
                } finally {
                    getProducts()
                }
            }
        })
    }

    const headerComponent = React.useMemo(() => {
        const handleClear = () => {
            if (filterText) setFilterText('')
        }
        return (
            <FilterComponent
                onFilter={(e) => setFilterText(e.target.value)}
                onClear={handleClear}
                filterText={filterText}
            />
        )
    }, [filterText])

    const columns = React.useMemo(() => [{
        name: '#',
        cell: (row, index) => <div>{index + 1}</div>,
        sortable: true,
    },
    {
        name: 'Producto',
        cell: (row) => <div>{row.name}</div>,
        sortable: true,
        selector: (row) => row.name
    },
    {
        name: 'Estado',
        cell: (row) => <div>{row.status}</div>,
        sortable: true,
        selector: (row) => row.status
    },   
    {
        name: 'Precio',
        cell: (row) => <div>${row.price}</div>,
        sortable: true,
        selector: (row) => row.price
    },    
    {
        name: 'Marca',
        cell: (row) => <div>{row.brand}</div>,
        sortable: true,
        selector: (row) => row.brand
    },    
    {
        name: 'Subcategoria',
        cell: (row) => <div>{row.subCategory.name}</div>,
        sortable: true,
        selector: (row) => row.subCategory.name
    },
    {
        name: 'Acciones',
        cell: (row) => <>
            <ButtonCircle
                icon='edit'
                type={'btn btn-outline-warning btn-circle'}
                size={16}
                onClick={() => {
                    setIsEditing(true)
                    setSelectedProduct(row)
                }}
            >

            </ButtonCircle>
            {row.status ? (
                <ButtonCircle
                    icon='trash-2'
                    type={'btn btn-outline-danger btn-circle'}
                    size={16}
                    onClick={() => {
                        enableOrDisable(row)
                    }}
                ></ButtonCircle>
            ) : (
                <ButtonCircle
                    icon='pocket'
                    type={'btn btn-outline-success btn-circle'}
                    size={16}
                    onClick={() => {
                        enableOrDisable(row)
                    }}
                ></ButtonCircle>
            )}
        </>//fragment
    }
    ])
    return <Card>
        <Card.Header>
            <Row>
                <Col>Productos</Col>
                <Col className='text-end'>
                    <ButtonCircle
                        type={'btn btn-outline-success'}
                        onClick={() => setIsOpen(true)}
                        icon='plus'
                        size={16}
                    />
                    <ProductForm
                        isOpen={isOpen}
                        onClose={() => setIsOpen(false)}
                        setProducts={setProducts}
                    />
                    {/* <EditProductForm
                        isOpen={isEditing}
                        onClose={() => setIsEditing(false)}
                        setProducts={setProducts}
                        Product={selectedProduct}
                    /> */}
                </Col>
            </Row>
        </Card.Header>
        <Card.Body>
            <DataTable
                columns={columns}
                data={filteredProducts}
                progressPending={isLoading}
                progressComponent={<Loading />}
                noDataComponent={'Sin registros'}
                pagination
                paginationComponentOptions={options}
                subHeader
                subHeaderComponent={headerComponent}
                persistTableHead
                striped={true}
                highlightOnHover={true}
            />
        </Card.Body>
    </Card>
}
