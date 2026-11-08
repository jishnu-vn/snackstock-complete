import {describe,it,expect} from 'vitest'; import {validProduct} from './validators';
describe('product validation',()=>{it('rejects invalid values',()=>expect(validProduct({name:'Pen',sku:'P1',price:0,stockQuantity:-1,reorderLevel:2})).toBe(false));it('accepts valid values',()=>expect(validProduct({name:'Pen',sku:'P1',price:1,stockQuantity:0,reorderLevel:2})).toBe(true));});
