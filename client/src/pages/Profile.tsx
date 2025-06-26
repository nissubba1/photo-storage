import {useState} from "react";
import {useEffect} from "react";
import {useSearchParams} from "react-router-dom";

function ImageCard({photo}) {
    return(
        <div className={"border rounded-md p-3 bg-white"}>
            <img src={photo.url} alt={photo.fileName}/>
        </div>
    )
}

export default function Profile() {

    const [photoList, setPhotoList] = useState<String[]>([]);
    const [searchParams] = useSearchParams()
    const username = searchParams.get("username")
    const fetchPhoto = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/photo-service/gallery/${username}`);
            const data = await response.json();
            console.log(data);

            if (!response.ok) {
                console.log("Error");
                throw new Error("Failed to fetch photo");
            }
            
            setPhotoList(data);

        } catch (error) {
            console.log(error);
        }
    }

    useEffect(() => {
        fetchPhoto().catch((err) => console.log(err))
    }, [])

    return(
        <section>
            <div>
                <h1>Nishan Profile</h1>
            </div>
            <div className={"grid grid-cols-4 gap-5"}>
                {photoList.map((photo, index) => (
                    <ImageCard key={index} photo={photo} />
                ))}
            </div>
        </section>
    )
}