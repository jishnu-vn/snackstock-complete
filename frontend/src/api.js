export const API_BASE=import.meta.env.VITE_API_BASE_URL||'http://localhost:8080/api';
const base=API_BASE;
export async function api(path,options={}){const res=await fetch(base+path,{headers:{'Content-Type':'application/json'},...options});if(!res.ok){const body=await res.json().catch(()=>({}));throw new Error(body.message||'Request failed');}return res.status===204?null:res.json();}
export async function uploadImage(file){const data=new FormData();data.append('image',file);const res=await fetch(base+'/uploads/product-image',{method:'POST',body:data});if(!res.ok){const body=await res.json().catch(()=>({}));throw new Error(body.message||'Upload failed');}return res.json();}
