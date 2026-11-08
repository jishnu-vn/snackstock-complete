export function validProduct(p){return Boolean(p.name?.trim()&&p.sku?.trim())&&Number(p.price)>0&&Number(p.stockQuantity)>=0&&Number(p.reorderLevel)>=0;}
