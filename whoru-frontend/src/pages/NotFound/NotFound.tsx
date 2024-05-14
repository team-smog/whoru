import notFound from '@/assets/@common/404NotFound.png'

const NotFound = () => {
  return (
    <div className='px-10 flex flex-col justify-center w-full max-w-[500px]  fixed top-1/4'>
      <p className='text-center text-[30px] text-text_color pb-5'>404 Not Found</p>
      <img src={notFound} className=''/>
    </div>
  )
}

export default NotFound;