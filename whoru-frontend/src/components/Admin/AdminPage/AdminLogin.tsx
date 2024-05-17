import { useState } from 'react'
import { useNavigate } from 'react-router-dom';

const AdminLogin = () => {
	const [id, setId] = useState<string>('');
	const [password, setPassword] = useState<string>('');
  const navigate = useNavigate();


	const handleLogin = (e: React.FormEvent) => {
		e.preventDefault();
    navigate('/admin');
	}

	return (
		<>
			<div className='px-4 translate-y-1/2'>
				<div className="flex flex-col">
					<label className="text-[16px] pb-2 text-text_color">ID</label>
					<input
						type="text"
						placeholder="아이디를 입력해주세요"
						value={id}
						onChange={(e) => setId(e.target.value)}
						className="w-full max-w-[500px] outline-none border-b p-2 text-[14px] text-text_color"
					/>
				</div>
				<div className="flex flex-col">
					<p className="text-[16px] pb-2 pt-4 text-text_color">PassWord</p>
					<input
						type="password"
						placeholder="비밀번호를 입력해주세요"
						value={password}
						onChange={(e) => setPassword(e.target.value)}
						className="w-full max-w-[500px] outline-none border-b p-2 text-[14px] text-text_color"
					/>
				</div>
				<div className='w-full max-w-[500px] mx-auto px-4 py-8' onClick={handleLogin}>
					<button className='bg-gray-200 w-full text-text_color rounded-[10px] p-2 text-md'>로그인</button>
				</div>
			</div>
		</>
	)
}

export default AdminLogin
